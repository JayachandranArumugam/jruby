/***** BEGIN LICENSE BLOCK *****
 * Version: EPL 2.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Eclipse Public
 * License Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/epl-v20.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the EPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the EPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/

package org.jruby.compiler;

import org.jruby.Ruby;
import org.jruby.ast.util.SexpMaker;
import org.jruby.ir.IRClosure;
import org.jruby.ir.targets.JVMVisitor;
import org.jruby.ir.targets.JVMVisitorMethodContext;
import org.jruby.runtime.CompiledIRBlockBody;
import org.jruby.runtime.MixedModeIRBlockBody;

import static org.jruby.compiler.MethodJITTask.*;

class BlockJITTask extends JITCompiler.Task {

    private final String className;
    private final MixedModeIRBlockBody body;
    private final String methodName;

    public BlockJITTask(JITCompiler jitCompiler, MixedModeIRBlockBody body, String className) {
        super(jitCompiler);
        this.body = body;
        this.className = className;
        this.methodName = body.getName();
    }

    @Override
    public void exec() throws NoSuchMethodException, IllegalAccessException {
        // Check if the method has been explicitly excluded
        String excludeModuleName = checkExcludedMethod(jitCompiler.config, className, methodName, body.getImplementationClass());
        if (excludeModuleName != null) {
            body.setCallCount(-1);
            if (jitCompiler.config.isJitLogging()) {
                JITCompiler.log(body, methodName, "skipping block in " + excludeModuleName);
            }
            return;
        }

        final String key = SexpMaker.sha1(body.getIRScope());
        final Ruby runtime = jitCompiler.runtime;
        JVMVisitor visitor = new JVMVisitor(runtime);
        BlockJITClassGenerator generator = new BlockJITClassGenerator(className, methodName, key, runtime, body, visitor);

        JVMVisitorMethodContext context = new JVMVisitorMethodContext();
        generator.compile(context);

        Class<?> sourceClass = defineClass(generator, visitor, body.getIRScope(), body.ensureInstrsReady());
        if (sourceClass == null) return; // class could not be found nor generated; give up on JIT and bail out

        // successfully got back a jitted body
        String jittedName = context.getVariableName();

        // blocks only have variable-arity
        body.completeBuild(
                new CompiledIRBlockBody(
                        JITCompiler.PUBLIC_LOOKUP.findStatic(sourceClass, jittedName, JVMVisitor.CLOSURE_SIGNATURE.type()),
                        body.getIRScope(),
                        ((IRClosure) body.getIRScope()).getSignature().encode()));
    }

    @Override
    protected void logJitted() {
        logImpl("block done jitting");
    }

    @Override
    protected void logFailed(final Throwable ex) {
        logImpl("could not compile block; passes run: " + body.getIRScope().getExecutedPasses(), ex);
    }

    @Override
    protected void logImpl(final String message, Object... reason) {
        JITCompiler.log(body, methodName, message, reason);
    }

}
