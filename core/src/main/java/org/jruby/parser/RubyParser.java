// created by jay 1.0.2 (c) 2002-2004 ats@cs.rit.edu
// skeleton Java 1.0 (c) 2002 ats@cs.rit.edu

					// line 2 "RubyParser.y"
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
 * Copyright (C) 2008-2017 Thomas E Enebo <enebo@acm.org>
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

package org.jruby.parser;

import java.io.IOException;

import org.jruby.ast.ArgsNode;
import org.jruby.ast.ArgumentNode;
import org.jruby.ast.ArrayNode;
import org.jruby.ast.AssignableNode;
import org.jruby.ast.BackRefNode;
import org.jruby.ast.BeginNode;
import org.jruby.ast.BlockAcceptingNode;
import org.jruby.ast.BlockArgNode;
import org.jruby.ast.BlockNode;
import org.jruby.ast.BlockPassNode;
import org.jruby.ast.BreakNode;
import org.jruby.ast.ClassNode;
import org.jruby.ast.ClassVarNode;
import org.jruby.ast.ClassVarAsgnNode;
import org.jruby.ast.Colon3Node;
import org.jruby.ast.ConstNode;
import org.jruby.ast.ConstDeclNode;
import org.jruby.ast.DStrNode;
import org.jruby.ast.DSymbolNode;
import org.jruby.ast.DXStrNode;
import org.jruby.ast.DefnNode;
import org.jruby.ast.DefsNode;
import org.jruby.ast.DotNode;
import org.jruby.ast.EncodingNode;
import org.jruby.ast.EnsureNode;
import org.jruby.ast.EvStrNode;
import org.jruby.ast.FalseNode;
import org.jruby.ast.FileNode;
import org.jruby.ast.FCallNode;
import org.jruby.ast.FixnumNode;
import org.jruby.ast.FloatNode;
import org.jruby.ast.ForNode;
import org.jruby.ast.GlobalAsgnNode;
import org.jruby.ast.GlobalVarNode;
import org.jruby.ast.HashNode;
import org.jruby.ast.IfNode;
import org.jruby.ast.InstAsgnNode;
import org.jruby.ast.InstVarNode;
import org.jruby.ast.IterNode;
import org.jruby.ast.LambdaNode;
import org.jruby.ast.ListNode;
import org.jruby.ast.LiteralNode;
import org.jruby.ast.ModuleNode;
import org.jruby.ast.MultipleAsgnNode;
import org.jruby.ast.NextNode;
import org.jruby.ast.NilImplicitNode;
import org.jruby.ast.NilNode;
import org.jruby.ast.Node;
import org.jruby.ast.NonLocalControlFlowNode;
import org.jruby.ast.NumericNode;
import org.jruby.ast.OpAsgnAndNode;
import org.jruby.ast.OpAsgnOrNode;
import org.jruby.ast.OptArgNode;
import org.jruby.ast.PostExeNode;
import org.jruby.ast.PreExe19Node;
import org.jruby.ast.RationalNode;
import org.jruby.ast.RedoNode;
import org.jruby.ast.RegexpNode;
import org.jruby.ast.RequiredKeywordArgumentValueNode;
import org.jruby.ast.RescueBodyNode;
import org.jruby.ast.RescueNode;
import org.jruby.ast.RestArgNode;
import org.jruby.ast.RetryNode;
import org.jruby.ast.ReturnNode;
import org.jruby.ast.SClassNode;
import org.jruby.ast.SelfNode;
import org.jruby.ast.StarNode;
import org.jruby.ast.StrNode;
import org.jruby.ast.TrueNode;
import org.jruby.ast.UnnamedRestArgNode;
import org.jruby.ast.UntilNode;
import org.jruby.ast.VAliasNode;
import org.jruby.ast.WhileNode;
import org.jruby.ast.XStrNode;
import org.jruby.ast.YieldNode;
import org.jruby.ast.ZArrayNode;
import org.jruby.ast.ZSuperNode;
import org.jruby.ast.types.ILiteralNode;
import org.jruby.common.IRubyWarnings;
import org.jruby.common.IRubyWarnings.ID;
import org.jruby.lexer.yacc.ISourcePosition;
import org.jruby.lexer.yacc.ISourcePositionHolder;
import org.jruby.lexer.LexerSource;
import org.jruby.lexer.LexingCommon;
import org.jruby.lexer.yacc.RubyLexer;
import org.jruby.lexer.yacc.StrTerm;
import org.jruby.lexer.yacc.SyntaxException.PID;
import org.jruby.util.ByteList;
import org.jruby.util.KeyValuePair;
import org.jruby.util.StringSupport;
import static org.jruby.lexer.LexingCommon.EXPR_BEG;
import static org.jruby.lexer.LexingCommon.EXPR_FITEM;
import static org.jruby.lexer.LexingCommon.EXPR_FNAME;
import static org.jruby.lexer.LexingCommon.EXPR_ENDFN;
import static org.jruby.lexer.LexingCommon.EXPR_ENDARG;
import static org.jruby.lexer.LexingCommon.EXPR_END;
import static org.jruby.lexer.LexingCommon.EXPR_LABEL;
import static org.jruby.parser.ParserSupport.value_expr;

 
public class RubyParser {
    protected ParserSupport support;
    protected RubyLexer lexer;

    public RubyParser(LexerSource source, IRubyWarnings warnings) {
        this.support = new ParserSupport();
        this.lexer = new RubyLexer(support, source, warnings);
        support.setLexer(lexer);
        support.setWarnings(warnings);
    }

    @Deprecated
    public RubyParser(LexerSource source) {
        this(new ParserSupport(), source);
    }

    @Deprecated
    public RubyParser(ParserSupport support, LexerSource source) {
        this.support = support;
        lexer = new RubyLexer(support, source);
        support.setLexer(lexer);
    }

    public void setWarnings(IRubyWarnings warnings) {
        support.setWarnings(warnings);
        lexer.setWarnings(warnings);
    }
					// line 165 "-"
  // %token constants
  public static final int keyword_class = 257;
  public static final int keyword_module = 258;
  public static final int keyword_def = 259;
  public static final int keyword_undef = 260;
  public static final int keyword_begin = 261;
  public static final int keyword_rescue = 262;
  public static final int keyword_ensure = 263;
  public static final int keyword_end = 264;
  public static final int keyword_if = 265;
  public static final int keyword_unless = 266;
  public static final int keyword_then = 267;
  public static final int keyword_elsif = 268;
  public static final int keyword_else = 269;
  public static final int keyword_case = 270;
  public static final int keyword_when = 271;
  public static final int keyword_while = 272;
  public static final int keyword_until = 273;
  public static final int keyword_for = 274;
  public static final int keyword_break = 275;
  public static final int keyword_next = 276;
  public static final int keyword_redo = 277;
  public static final int keyword_retry = 278;
  public static final int keyword_in = 279;
  public static final int keyword_do = 280;
  public static final int keyword_do_cond = 281;
  public static final int keyword_do_block = 282;
  public static final int keyword_return = 283;
  public static final int keyword_yield = 284;
  public static final int keyword_super = 285;
  public static final int keyword_self = 286;
  public static final int keyword_nil = 287;
  public static final int keyword_true = 288;
  public static final int keyword_false = 289;
  public static final int keyword_and = 290;
  public static final int keyword_or = 291;
  public static final int keyword_not = 292;
  public static final int modifier_if = 293;
  public static final int modifier_unless = 294;
  public static final int modifier_while = 295;
  public static final int modifier_until = 296;
  public static final int modifier_rescue = 297;
  public static final int keyword_alias = 298;
  public static final int keyword_defined = 299;
  public static final int keyword_BEGIN = 300;
  public static final int keyword_END = 301;
  public static final int keyword__LINE__ = 302;
  public static final int keyword__FILE__ = 303;
  public static final int keyword__ENCODING__ = 304;
  public static final int keyword_do_lambda = 305;
  public static final int tIDENTIFIER = 306;
  public static final int tFID = 307;
  public static final int tGVAR = 308;
  public static final int tIVAR = 309;
  public static final int tCONSTANT = 310;
  public static final int tCVAR = 311;
  public static final int tLABEL = 312;
  public static final int tCHAR = 313;
  public static final int tUPLUS = 314;
  public static final int tUMINUS = 315;
  public static final int tUMINUS_NUM = 316;
  public static final int tPOW = 317;
  public static final int tCMP = 318;
  public static final int tEQ = 319;
  public static final int tEQQ = 320;
  public static final int tNEQ = 321;
  public static final int tGEQ = 322;
  public static final int tLEQ = 323;
  public static final int tANDOP = 324;
  public static final int tOROP = 325;
  public static final int tMATCH = 326;
  public static final int tNMATCH = 327;
  public static final int tDOT = 328;
  public static final int tDOT2 = 329;
  public static final int tDOT3 = 330;
  public static final int tAREF = 331;
  public static final int tASET = 332;
  public static final int tLSHFT = 333;
  public static final int tRSHFT = 334;
  public static final int tANDDOT = 335;
  public static final int tCOLON2 = 336;
  public static final int tCOLON3 = 337;
  public static final int tOP_ASGN = 338;
  public static final int tASSOC = 339;
  public static final int tLPAREN = 340;
  public static final int tLPAREN2 = 341;
  public static final int tRPAREN = 342;
  public static final int tLPAREN_ARG = 343;
  public static final int tLBRACK = 344;
  public static final int tRBRACK = 345;
  public static final int tLBRACE = 346;
  public static final int tLBRACE_ARG = 347;
  public static final int tSTAR = 348;
  public static final int tSTAR2 = 349;
  public static final int tAMPER = 350;
  public static final int tAMPER2 = 351;
  public static final int tTILDE = 352;
  public static final int tPERCENT = 353;
  public static final int tDIVIDE = 354;
  public static final int tPLUS = 355;
  public static final int tMINUS = 356;
  public static final int tLT = 357;
  public static final int tGT = 358;
  public static final int tPIPE = 359;
  public static final int tBANG = 360;
  public static final int tCARET = 361;
  public static final int tLCURLY = 362;
  public static final int tRCURLY = 363;
  public static final int tBACK_REF2 = 364;
  public static final int tSYMBEG = 365;
  public static final int tSTRING_BEG = 366;
  public static final int tXSTRING_BEG = 367;
  public static final int tREGEXP_BEG = 368;
  public static final int tWORDS_BEG = 369;
  public static final int tQWORDS_BEG = 370;
  public static final int tSTRING_DBEG = 371;
  public static final int tSTRING_DVAR = 372;
  public static final int tSTRING_END = 373;
  public static final int tLAMBDA = 374;
  public static final int tLAMBEG = 375;
  public static final int tNTH_REF = 376;
  public static final int tBACK_REF = 377;
  public static final int tSTRING_CONTENT = 378;
  public static final int tINTEGER = 379;
  public static final int tIMAGINARY = 380;
  public static final int tFLOAT = 381;
  public static final int tRATIONAL = 382;
  public static final int tREGEXP_END = 383;
  public static final int tSYMBOLS_BEG = 384;
  public static final int tQSYMBOLS_BEG = 385;
  public static final int tDSTAR = 386;
  public static final int tSTRING_DEND = 387;
  public static final int tLABEL_END = 388;
  public static final int tLOWEST = 389;
  public static final int yyErrorCode = 256;

  /** number of final state.
    */
  protected static final int yyFinal = 1;

  /** parser tables.
      Order is mandated by <i>jay</i>.
    */
  protected static final short[] yyLhs = {
//yyLhs 648
    -1,   152,     0,   139,   140,   140,   140,   140,   141,   141,
    37,    36,    38,    38,    38,    38,    44,   155,    44,   156,
    39,    39,    39,    39,    39,    39,    39,    39,    39,    39,
    39,    39,    39,    39,    39,    39,    31,    31,    31,    31,
    31,    31,    31,    31,    61,    61,    61,    40,    40,    40,
    40,    40,    40,    45,    32,    32,    60,    60,   113,   148,
    43,    43,    43,    43,    43,    43,    43,    43,    43,    43,
    43,   116,   116,   127,   127,   117,   117,   117,   117,   117,
   117,   117,   117,   117,   117,    74,    74,   103,   103,   104,
   104,    75,    75,    75,    75,    75,    75,    75,    75,    75,
    75,    75,    75,    75,    75,    75,    75,    75,    75,    75,
    80,    80,    80,    80,    80,    80,    80,    80,    80,    80,
    80,    80,    80,    80,    80,    80,    80,    80,    80,     8,
     8,    30,    30,    30,     7,     7,     7,     7,     7,   120,
   120,   121,   121,    64,   158,    64,     6,     6,     6,     6,
     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,
     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,
     6,     6,     6,     6,     6,     6,   134,   134,   134,   134,
   134,   134,   134,   134,   134,   134,   134,   134,   134,   134,
   134,   134,   134,   134,   134,   134,   134,   134,   134,   134,
   134,   134,   134,   134,   134,   134,   134,   134,   134,   134,
   134,   134,   134,   134,   134,   134,   134,   134,    41,    41,
    41,    41,    41,    41,    41,    41,    41,    41,    41,    41,
    41,    41,    41,    41,    41,    41,    41,    41,    41,    41,
    41,    41,    41,    41,    41,    41,    41,    41,    41,    41,
    41,    41,    41,    41,    41,    41,    41,   136,   136,   136,
   136,    51,    51,    76,    79,    79,    79,    79,    62,    62,
    54,    58,    58,   130,   130,   130,   130,   130,    52,    52,
    52,    52,    52,   160,    56,   107,   106,   106,    82,    82,
    82,    82,    35,    35,    73,    73,    73,    42,    42,    42,
    42,    42,    42,    42,    42,    42,    42,    42,   161,    42,
   162,    42,   163,   164,    42,    42,    42,    42,    42,    42,
    42,    42,    42,    42,    42,    42,    42,    42,    42,    42,
    42,    42,    42,   166,   168,    42,   169,   170,    42,    42,
    42,   171,   172,    42,   173,    42,   175,   176,    42,   177,
    42,   178,   179,    42,   180,   181,    42,    42,    42,    42,
    42,    46,   150,   151,   149,   165,   165,   165,   167,   167,
    49,    49,    47,    47,   129,   129,   131,   131,    87,    87,
   132,   132,   132,   132,   132,   132,   132,   132,   132,    94,
    94,    94,    94,    93,    93,    69,    69,    69,    69,    69,
    69,    69,    69,    69,    69,    69,    69,    69,    69,    69,
    71,    71,    70,    70,    70,   124,   124,   123,   123,   133,
   133,   182,   126,    68,    68,   125,   125,   112,    59,    59,
    59,    59,    22,    22,    22,    22,    22,    22,    22,    22,
    22,   111,   111,   183,   114,   184,   115,    77,    48,    48,
   118,   118,    78,    78,    78,    50,    50,    53,    53,    28,
    28,    28,    15,    16,    16,    16,    17,    18,    19,    25,
    84,    84,    27,    27,    90,    88,    88,    26,    91,    83,
    83,    89,    89,    20,    20,    21,    21,    24,    24,    23,
   185,    23,   186,   187,   188,   189,   190,    23,    65,    65,
    65,    65,     2,     1,     1,     1,     1,    29,    33,    33,
    34,    34,    34,    34,    57,    57,    57,    57,    57,    57,
    57,    57,    57,    57,    57,    57,   119,   119,   119,   119,
   119,   119,   119,   119,   119,   119,   119,   119,    66,    66,
   191,    55,    55,    72,   192,    72,    95,    95,    95,    95,
    92,    92,    67,    67,    67,    67,    67,    67,    67,    67,
    67,    67,    67,    67,    67,    67,    67,   135,   135,   135,
   135,     9,     9,   147,   122,   122,    85,    85,   144,    96,
    96,    97,    97,    98,    98,    99,    99,   142,   142,   143,
   143,    63,   128,   105,   105,    86,    86,    10,    10,    13,
    13,    12,    12,   110,   109,   109,    14,   193,    14,   100,
   100,   101,   101,   102,   102,   102,   102,     3,     3,     3,
     4,     4,     4,     4,     5,     5,     5,    11,    11,   145,
   145,   146,   146,   153,   153,   157,   157,   137,   138,   159,
   159,   159,   174,   174,   154,   154,    81,   108,
    }, yyLen = {
//yyLen 648
     2,     0,     2,     2,     1,     1,     3,     2,     1,     4,
     4,     2,     1,     1,     3,     2,     1,     0,     5,     0,
     4,     3,     3,     3,     2,     3,     3,     3,     3,     3,
     4,     1,     3,     3,     3,     1,     3,     3,     6,     5,
     5,     5,     5,     3,     1,     3,     1,     1,     3,     3,
     3,     2,     1,     1,     1,     1,     1,     4,     3,     1,
     2,     3,     4,     5,     4,     5,     2,     2,     2,     2,
     2,     1,     3,     1,     3,     1,     2,     3,     5,     2,
     4,     2,     4,     1,     3,     1,     3,     2,     3,     1,
     3,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     4,     3,     3,     3,     3,     2,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     4,     3,     3,     3,     3,     2,     1,     1,
     1,     2,     1,     3,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     0,     4,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     3,     3,
     6,     5,     5,     5,     5,     4,     3,     3,     3,     3,
     3,     3,     3,     3,     3,     4,     2,     2,     3,     3,
     3,     3,     1,     3,     3,     3,     3,     3,     2,     2,
     3,     3,     3,     3,     3,     6,     1,     1,     1,     1,
     1,     3,     3,     1,     1,     2,     4,     2,     1,     3,
     3,     1,     1,     1,     1,     2,     4,     2,     1,     2,
     2,     4,     1,     0,     2,     2,     2,     1,     1,     2,
     3,     4,     1,     1,     3,     4,     2,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     0,     4,
     0,     3,     0,     0,     5,     3,     3,     2,     3,     3,
     1,     4,     3,     1,     5,     4,     3,     2,     1,     2,
     2,     6,     6,     0,     0,     7,     0,     0,     7,     5,
     4,     0,     0,     9,     0,     6,     0,     0,     8,     0,
     5,     0,     0,     7,     0,     0,     9,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     2,     1,     1,
     1,     5,     1,     2,     1,     1,     1,     3,     1,     3,
     1,     4,     6,     3,     5,     2,     4,     1,     3,     4,
     2,     2,     1,     2,     0,     6,     8,     4,     6,     4,
     2,     6,     2,     4,     6,     2,     4,     2,     4,     1,
     1,     1,     3,     1,     4,     1,     4,     1,     3,     1,
     1,     0,     3,     4,     1,     3,     3,     3,     2,     4,
     5,     5,     2,     4,     4,     3,     3,     3,     2,     1,
     4,     3,     3,     0,     3,     0,     3,     5,     1,     1,
     6,     0,     1,     1,     1,     2,     1,     2,     1,     1,
     1,     1,     1,     1,     1,     2,     3,     3,     3,     4,
     0,     3,     1,     2,     4,     0,     3,     4,     4,     0,
     3,     0,     3,     0,     2,     0,     2,     0,     2,     1,
     0,     3,     0,     0,     0,     0,     0,     8,     1,     1,
     1,     1,     2,     1,     1,     1,     1,     3,     1,     2,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     0,     4,     0,     3,     0,     3,     4,     2,     2,     1,
     2,     0,     6,     8,     4,     6,     4,     6,     2,     4,
     6,     2,     4,     2,     4,     1,     0,     1,     1,     1,
     1,     1,     1,     1,     1,     3,     1,     3,     1,     2,
     1,     2,     1,     1,     3,     1,     3,     1,     1,     2,
     1,     3,     3,     1,     3,     1,     3,     1,     1,     2,
     1,     1,     1,     2,     2,     0,     1,     0,     4,     1,
     2,     1,     3,     3,     2,     4,     2,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     0,     1,     0,     1,     2,     2,     0,
     1,     1,     1,     1,     1,     2,     0,     0,
    }, yyDefRed = {
//yyDefRed 1105
     1,     0,     0,     0,   362,   363,     0,     0,   308,     0,
     0,     0,   333,   336,     0,     0,     0,   359,   360,   364,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
   463,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   483,   485,   487,     0,     0,   421,   538,
   539,   510,   513,   511,   512,     0,     0,   460,    59,   298,
     0,   464,   299,   300,     0,   301,   302,   297,   461,    31,
    47,   459,   508,     0,     0,     0,     0,     0,     0,     0,
   305,     0,    55,     0,     0,    85,     0,     4,   303,   304,
     0,     0,    71,     0,     2,     0,     5,     0,     0,     0,
     0,     7,   186,   197,   187,   210,   183,   203,   193,   192,
   213,   214,   208,   191,   190,   185,   211,   215,   216,   195,
   184,   198,   202,   204,   196,   189,   205,   212,   207,     0,
     0,     0,     0,   182,   201,   200,   217,   181,   188,   179,
   180,     0,     0,     0,     0,   136,   516,   515,     0,   518,
   171,   172,   168,   149,   150,   151,   158,   155,   157,   152,
   153,   173,   174,   159,   160,   607,   165,   164,   148,   170,
   167,   166,   162,   163,   156,   154,   146,   169,   147,   175,
   161,   137,   351,     0,   606,   138,   206,   199,   209,   194,
   176,   177,   178,   134,   135,   140,   139,   142,     0,   141,
   143,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   642,   643,     0,     0,     0,   644,     0,
     0,   357,   358,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,   361,     0,     0,   374,   375,     0,     0,   320,     0,
     0,     0,     0,   483,     0,     0,   278,    69,     0,     0,
     0,   611,   282,    70,     0,    67,     0,     0,   438,    66,
     0,   636,     0,     0,    19,     0,     0,     0,   236,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,    13,
    12,     0,     0,     0,     0,     0,   264,     0,     0,     0,
   609,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
   249,    51,   248,   505,   504,   506,   502,   503,     0,     0,
     0,     0,   470,   479,   330,     0,   475,   481,   465,   445,
   443,   329,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   259,   260,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
   258,   257,     0,     0,     0,     0,   445,   428,   629,   630,
     0,     0,     0,     0,   632,   631,     0,     0,    87,     0,
     0,     0,     0,     0,     0,     3,     0,   432,     0,   327,
    68,   520,   519,   521,   522,   524,   523,   525,     0,     0,
     0,     0,   132,     0,     0,   306,   349,     0,   352,   627,
   628,   354,   144,     0,     0,     0,   366,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,   645,
     0,     0,     0,   509,     0,     0,     0,     0,   341,   614,
   289,   285,     0,   616,     0,     0,   279,   287,     0,   280,
     0,   322,     0,   284,   274,   273,     0,     0,     0,     0,
   326,    50,    21,    23,    22,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   315,    11,     0,
     0,   311,     0,   318,     0,   640,   265,     0,   267,   319,
   610,     0,    89,     0,     0,     0,     0,     0,   492,   490,
   507,   489,   486,   466,   484,   467,   468,   488,     0,     0,
   572,   569,   568,   567,   570,   578,   587,     0,     0,   598,
   597,   602,   601,   588,   573,     0,     0,     0,   595,   424,
     0,     0,     0,   565,   585,     0,   549,   576,   571,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,    25,
    26,    27,    28,    29,    48,    49,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,   435,     0,   437,     0,     0,   622,
     0,     0,   623,   436,     0,   620,   621,     0,    46,     0,
     0,     0,    43,   226,     0,     0,     0,     0,    36,   218,
    33,   288,     0,     0,     0,     0,    88,    32,    34,   292,
     0,    37,   219,     6,   443,    61,     0,   129,     0,   131,
   540,   344,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,   309,     0,   367,     0,     0,     0,     0,     0,
     0,     0,     0,   340,   369,   334,   368,   337,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   613,     0,     0,
     0,   286,   612,   321,   637,     0,     0,   270,   325,    20,
     0,     9,    30,     0,   225,     0,     0,    14,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   493,     0,   469,
   472,     0,   477,     0,     0,     0,   376,     0,   378,     0,
     0,   599,   603,     0,   563,     0,     0,   422,     0,   558,
     0,   561,     0,   547,   589,     0,   548,   579,     0,   474,
     0,   478,     0,   442,   413,     0,   411,     0,   410,   441,
     0,     0,   427,     0,     0,   434,     0,     0,     0,     0,
     0,   272,     0,   433,   271,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,    86,     0,     0,     0,     0,
     0,     0,     0,     0,   133,     0,     0,   608,     0,     0,
     0,   355,   145,   453,     0,     0,   454,     0,     0,   372,
     0,   370,     0,     0,     0,     0,     0,     0,     0,   339,
     0,     0,     0,     0,     0,     0,   615,   291,   281,     0,
   324,     0,   314,   266,    90,     0,   494,   498,   499,   500,
   491,   501,   471,   473,   480,     0,     0,     0,     0,   575,
     0,     0,     0,   550,   574,     0,     0,     0,     0,   577,
     0,   596,     0,   586,   604,     0,   591,   476,   482,     0,
     0,     0,   409,   583,     0,     0,   392,     0,   593,     0,
     0,     0,   446,   444,     0,    42,   223,    41,   224,    65,
     0,   638,    39,   221,    40,   222,    63,   431,   430,    45,
     0,     0,     0,     0,     0,     0,     0,     0,     0,    58,
   347,     0,     0,   440,   350,     0,     0,     0,     0,     0,
     0,   456,   373,     0,    10,   458,     0,   331,     0,   332,
   290,     0,     0,     0,   342,     0,    18,   495,   377,     0,
     0,     0,   379,   423,     0,     0,   564,   426,   425,     0,
   556,     0,   554,     0,   559,   562,   546,     0,   407,     0,
     0,   402,     0,   390,     0,   405,   412,   391,     0,     0,
     0,     0,     0,     0,     0,    38,     0,     0,     0,   541,
   345,   543,   353,   545,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
   455,     0,   457,     0,   448,   447,   449,   335,   338,     0,
   496,     0,     0,     0,     0,   419,     0,   417,   420,     0,
     0,     0,     0,     0,   393,   414,     0,     0,   584,     0,
     0,     0,   594,   317,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,   416,
   557,     0,   552,   555,   560,     0,   408,     0,   399,     0,
   397,   389,     0,   403,   406,     0,     0,   348,   356,     0,
     0,     0,     0,     0,   450,   371,   343,     0,     0,   418,
     0,     0,     0,     0,     0,     0,   497,   553,   401,     0,
   395,   398,   404,     0,   396,
    }, yyDgoto = {
//yyDgoto 194
     1,   346,    67,    68,   690,   614,   615,   206,   432,   554,
   555,   441,   556,   557,   193,    69,    70,    71,    72,    73,
   349,   348,    74,   532,   351,    75,    76,   731,    77,    78,
   433,    79,    80,    81,    82,   648,   443,   444,   307,   308,
    84,    85,    86,    87,   309,   227,   299,   821,  1015,   822,
   930,    89,   484,   934,   616,   661,   285,    90,   783,    91,
    92,   638,   639,   558,   208,   850,   229,   559,   560,   880,
   766,   767,   809,   640,    94,    95,   278,   458,   815,   315,
   230,   310,   486,   539,   538,   561,   562,   737,   573,   574,
    98,    99,   744,   968,  1034,   863,   564,   883,   884,   565,
   321,   487,   281,   100,   523,   885,   476,   282,   477,   753,
   566,   419,   397,   655,   577,   575,   101,   102,   671,   231,
   209,   210,   567,  1026,   860,   747,   354,   312,   888,   266,
   488,   738,   739,  1027,   195,   568,   395,   481,   777,   104,
   105,   106,   569,   570,   571,   664,   406,   864,   107,   108,
   109,   110,     2,   236,   237,   505,   495,   482,   669,   516,
   286,   211,   313,   314,   718,   447,   239,   685,   832,   240,
   833,   695,  1019,   801,   448,   799,   988,   665,   438,   667,
   668,   928,   355,   578,   576,   728,   727,   846,   947,  1020,
  1055,   800,   810,   437,
    }, yySindex = {
//yySindex 1105
     0,     0, 19772, 21071,     0,     0, 19137, 19527,     0, 22232,
 22232, 18348,     0,     0,  4137, 20161, 20161,     0,     0,     0,
  -245,  -224,     0,     0,     0,     0,    33, 19397,   134,  -203,
  -198,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0, 22361, 22361,   435,  -136, 19902,     0, 20551, 20941, 18616,
 22361, 22490, 19267,     0,     0,     0,   155,   186,     0,     0,
     0,     0,     0,     0,     0,   198,   219,     0,     0,     0,
  -109,     0,     0,     0,  -120,     0,     0,     0,     0,     0,
     0,     0,     0,  1731,   -75,  5128,     0,    -4,   897,   776,
     0,   507,     0,   -39,   245,     0,   240,     0,     0,     0,
 22748,   255,     0,    -6,     0,   180,     0,  -158, 20161, 22877,
 23006,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,  -125,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   290,     0,
     0, 20032,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,    61, 20032,   -75,    94,   922,     7,
   363,   121,    94,     0,     0,   180,   253,   470,     0, 22232,
 22232,     0,     0,  -245,  -224,     0,     0,     0,     0,   249,
   134,     0,     0,     0,     0,     0,     0,     0,     0,   435,
   232,     0,  1080,     0,     0,     0,   265,  -158,     0, 22361,
 22361, 22361, 22361,     0, 22361,  5128,     0,     0,   214,   549,
   552,     0,     0,     0, 16728,     0, 20161, 20161,     0,     0,
 18482,     0, 22232,  -127,     0, 21329, 19772, 20032,     0,  1185,
   282,   285,   276, 21200,     0, 19902,   279,   180,  1731,     0,
     0,     0,   134,   134, 21200,   305,     0,   108,   237,   214,
     0,   268,   237,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   323, 23135,  1201,     0,   610,
     0,     0,     0,     0,     0,     0,     0,     0,   783,   946,
  1033,   387,     0,     0,     0,  3008,     0,     0,     0,     0,
     0,     0, 22232, 22232, 22232, 22232, 21200, 22232, 22232, 22361,
 22361, 22361, 22361, 22361,     0,     0, 22361, 22361, 22361, 22361,
 22361, 22361, 22361, 22361, 22361, 22361, 22361, 22361, 22361, 22361,
     0,     0, 22361, 22361, 22361, 22361,     0,     0,     0,     0,
  3620, 20161,  6507, 22361,     0,     0, 23865, 22490,     0, 21458,
 19902, 18750,   612, 21458, 22490,     0, 18879,     0,   312,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
 22232,    75,     0,   331,  1212,     0,     0, 22232,     0,     0,
     0,     0,     0,   400,   414,   276,     0, 20032,   423,  6933,
 20161,  7011, 22361, 22361, 22361, 20032,   253, 21587,   434,     0,
   235,   235,   353,     0,     0,  7067, 20161,  7437,     0,     0,
     0,     0,   973,     0, 22361, 20291,     0,     0, 20681,     0,
   134,     0,   359,     0,     0,     0,   668,   674,   134,    64,
     0,     0,     0,     0,     0, 19527, 22232,  5128,   377,   385,
  6933,  7011, 22361, 22361,  1731,   402,   134,     0,     0, 19008,
     0,     0,  1731,     0, 20811,     0,     0, 20941,     0,     0,
     0,     0,     0,   705,  7515, 20161,  7571, 23135,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,  1089,   -49,
     0,     0,     0,     0,     0,     0,     0,  1694,  3027,     0,
     0,     0,     0,     0,     0,   456,   461,   734,     0,     0,
  -154,   740,   745,     0,     0,   748,     0,     0,     0,   493,
   759, 22361,   762,  1153,   -35,   561,   -18,   463,   -18,     0,
     0,     0,     0,     0,     0,     0,   282,  4139,  4139,  4139,
  4139,  4026,  4560,  4139,  4139,  3552,  3552,  1393,  1393,   282,
  3035,   282,   282,   829,   829,  3455,  3455,  7938,  3153,   566,
   498,     0,   500,  -224,     0,     0,     0,   134,   510,     0,
   512,  -224,     0,     0,  3153,     0,     0,  -224,     0,   563,
  4643,  1266,     0,     0,   -39,   801, 22361,  4643,     0,     0,
     0,     0,   821,   134, 23135,   828,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,   -75,     0,     0,     0,
     0,     0, 23473, 20161, 23529, 20032,    64,   504, 19657, 19527,
 21716,   604,     0,   342,     0,   538,   546,   134,   554,   556,
   604,   636,   584,     0,     0,     0,     0,     0,     0,     0,
  -224,   134,     0,     0,  -224, 22232, 22361,     0, 22361,   214,
   552,     0,     0,     0,     0, 20291, 20681,     0,     0,     0,
    64,     0,     0,   282,     0, 19772,     0,     0,   134,   237,
 23135,     0,     0,   134,     0,     0,   705,     0,   550,     0,
     0,   201,     0,   876,  1694,   627,     0,   866,     0,   134,
   134,     0,     0,  3143,     0, 20032, 20032,     0,  3027,     0,
  3027,     0,   562,     0,     0,   286,     0,     0, 22361,     0,
   211,     0,   882,     0,     0,   773,     0, 20032,     0,     0,
 20032,   863,     0, 22490, 22490,     0,   312,   586,   595, 22490,
 22490,     0,   312,     0,     0,    -4,  -120, 21200, 22361, 23585,
 20161, 23641, 22490,     0, 21845,     0,   705, 23135,   581,   180,
 22232, 20032,     0,     0,     0,   134,   665,     0,  3027, 20032,
  3027,     0,     0,     0,     0,   611,     0, 20032,   691,     0,
 22232,     0,   692, 22361, 22361,   617, 22361, 22361,   696,     0,
 21974, 20032, 20032, 20032,     0,   235,     0,     0,     0,   918,
     0,   608,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   134,  1413,   933,  1989,     0,
   639,   925,   942,     0,     0,   726,   641,   955,   957,     0,
   958,     0,   942,     0,     0,   759,     0,     0,     0,   967,
   134,   972,     0,     0,   974,   975,     0,   666,     0,   759,
 23264,   965,     0,     0, 22361,     0,     0,     0,     0,     0,
 22490,     0,     0,     0,     0,     0,     0,     0,     0,     0,
  5128,   498,   500,   134,   510,   512, 22361,     0,   705,     0,
     0,   180,   764,     0,     0,   134,   767,   180,   504, 23393,
    94,     0,     0, 20032,     0,     0,    94,     0, 22361,     0,
     0,   -98,   768,   771,     0, 20681,     0,     0,     0,   993,
  1413,  1082,     0,     0,  1609,  3143,     0,     0,     0,  3143,
     0,  3027,     0,  3143,     0,     0,     0,  3143,     0,   679,
  3027,     0,   562,     0,  3027,     0,     0,     0,     0,     0,
   732,  1271, 23264,  5128,  5128,     0,   586,     0, 20032,     0,
     0,     0,     0,     0, 20032,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,   735,  1306,     0,
     0, 20032,     0, 20032,     0,     0,     0,     0,     0, 20032,
     0,  1413,   993,  1413,  1000,     0,   585,     0,     0,   942,
  1002,   942,   942,  1006,     0,     0,  1009,  1013,     0,   759,
  1016,  1006,     0,     0, 23697,  1271,   806,   807,     0, 23753,
 20161, 23809,   400,   342,   809, 20032,   993,  1413,  1609,     0,
     0,  3143,     0,     0,     0,  3143,     0,  3143,     0,  3027,
     0,     0,  3143,     0,     0,     0,     0,     0,     0,     0,
     0,   134,     0,     0,     0,     0,     0,   689,   993,     0,
   942,  1006,  1042,  1006,  1006,     0,     0,     0,     0,  3143,
     0,     0,     0,  1006,     0,
    }, yyRindex = {
//yyRindex 1105
     0,     0,   300,     0,     0,     0,     0,     0,     0,     0,
     0,   831,     0,     0,     0, 11195, 11317,     0,     0,     0,
  5374,  4912, 12631, 12708, 12828, 13009, 22619,     0, 22103,     0,
     0, 13129, 13206, 13310,  5705,  3904, 13507, 13611,  5836, 13688,
     0,     0,     0,     0,     0,   120, 18213,   763,   747,    51,
     0,     0,  1237,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
 10583,     0,     0,     0, 10705,     0,     0,     0,     0,     0,
     0,     0,     0,    80,  1354,  1633, 10889, 12932,     0, 13859,
     0, 16251,     0, 13989,     0,     0,     0,     0,     0,     0,
   195,     0,     0,     0,     0,    35,     0, 20421, 11501,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,  1147,
  3510,  4014,  4518,     0,     0,     0,     0,     0,     0,     0,
     0,  5001,  5463,  5946,  5993,     0,     0,     0,  6450,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,  9283,     0,
     0,    49,  8441,  8563,  8747,  8869,  9053,  9175,  9359,  2762,
  9481,  9665,  2896,  9787,     0,   120,  1977,     0,     0, 10277,
     0,     0,     0,     0,     0,   831,     0,   833,     0,     0,
     0,     0,     0, 11011,  9971,   808,  1154,  1251,  1877,     0,
   770,  2257,  2281,  2287,  3106,  2504,  2667,  5068,  3183,     0,
     0,     0,     0,  3556,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0, 16380,     0,     0,  1997,  3135,
  3135,     0,     0,     0,   774,     0,     0,    74,     0,     0,
   774,     0,     0,     0,     0,     0,    79,    79,     0,     0,
 11623,  2308, 14099,     0, 17675,   120,     0,  1784,   634,     0,
     0,   130,   774,   774,     0,     0,     0,   772,   772,     0,
     0,     0,   765,  1175,  1864,  1988,  2054,  2171,  2663,  5896,
  1086,  6343,  6847,  1104,  7351,     0,     0,     0,  7855,   203,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,  -150,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,    68,     0,     0,     0,     0,     0,     0,     0,     0,
   120,   204,   318,     0,     0,     0,    42,     0, 11445,     0,
     0,     0,     0,     0,     0,     0,     0,     0, 16861, 16999,
     0,     0,     0, 17810,     0,     0,     0,     0,     0,     0,
     0,     0,     0,   383,     0, 10399,     0,   873, 17541,     0,
    68,     0,     0,     0,     0,    83,     0,     0,     0,     0,
     0,     0,     0,     0,  3687,     0,    68,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
   774,     0,     0,     0,     0,     0,   105,   105,   774,   774,
     0,     0,     0,     0,     0,     0,     0,  2193,     0,     0,
     0,     0,     0,     0,   701,     0,   774,     0,     0,  3024,
   125,     0,   153,     0,   782,     0,     0,   -86,     0,     0,
     0,  8650,     0,   321,     0,    68,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,   160,     0,
     0,     0,     0,     0,     0,   217,     0,   243,     0,     0,
     0,   243,   243,     0,     0,   548,     0,     0,     0,   386,
   548,   298,   378,     0,     0,     0, 17944,     0, 18079,     0,
     0,     0,     0,     0,     0,     0, 11744,  1105, 15359, 15474,
 15600, 15703, 15976, 15792, 15878,  2437, 16065, 14549, 14658, 11866,
 14775, 11987, 12114, 14211, 14320, 14901, 15004,  1071, 15130,     0,
  6209,  4277,  7721, 20421,     0,  4408,     0,   786,  6340,     0,
  6713,  5243,     0,     0, 15245,     0,     0,  8094,     0,  2523,
  1676,     0,     0,     0, 14437,     0,     0,  8671,     0,     0,
     0,     0,     0,   774,     0,   577,     0,     0,     0,     0,
  8977,     0,     0,     0,     0,     0,   221,     0, 17405,     0,
     0,     0,     0,    68,     0,    49,   774,   999,     0,     0,
   613,   468,     0,   871,     0,  3269,  4781,   786,  3400,  3773,
   871,     0,     0,     0,     0,     0,     0,     0,  5530,   784,
     0,   786,  6092,  6596, 10093,     0,     0,     0,     0, 16466,
  3135,     0,     0,     0,     0,   146,   191,     0,     0,     0,
   774,     0,     0, 12216,     0,    79,    38,     0,   774,   772,
     0,  1262,   638,   786,  1471,  1758,   578,     0,     0,     0,
     0,     0,     0,     0,     0,   162,     0,   172,     0,   774,
    87,     0,     0,     0,     0,   746,    79,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   -10,     0,    49,     0,     0,
    79,     0,     0,     0,     0,     0, 12466, 12325,     0,     0,
     0,     0, 13432,     0,     0, 16524,  2581,     0,     0,     0,
    68,     0,     0, 16564,     0,     0,   607,     0,     0,     0,
     0,    49, 17133, 17271,     0,   786,     0,     0,   178,    49,
   250,     0,     0,     0,   741,   179,     0,   625,   871,     0,
     0,     0,     0,     0,     0,  8225,     0,     0,     0,     0,
     0,   753,   746,   746,  1447,     0,     0,     0,     0,   105,
     0,     0,     0,     0,     0,  2023,     0,     0,     0,     0,
     0,     0,     0,     0,     0,   774,     0,   185,     0,     0,
     0,  -184,   243,     0,     0,     0,     0,   243,   243,     0,
   243,     0,   243,     0,     0,   548,     0,     0,     0,    76,
   -10,    76,     0,     0,    95,    76,     0,     0,     0,    95,
    62,   106,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
 16104,  6844,  7852,   786,  7217,  7348,     0, 16601,   643,     0,
     0,     0,     0,     0,     0,   774,     0,     0,   999,     0,
     0,     0,     0,   746,     0,     0,     0,     0,     0,     0,
     0,   871,     0,     0,     0,   225,     0,     0,     0,   189,
     0,   212,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
   119,     0,     0,     0,     0,     0,     0,     0,  1143,  2033,
     0,   117,     0, 16191, 16294,     0, 12522, 16621,    49,     0,
     0,     0,     0,     0,    49,   731,  1379,  1427,  1618,  1816,
  1840,  2453,  1541,  2555,  2658,  1734,  2678,     0,     0,  2680,
     0,    49,     0,   873,     0,     0,     0,     0,     0,   746,
     0,     0,   213,     0,   226,     0,   -88,     0,     0,   243,
   243,   243,   243,    76,     0,     0,    76,    76,     0,    95,
    76,    76,     0,     0,     0,   118,     0,     0,  2682,     0,
    68,     0,   383,   871,     0,    41,   236,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,  2118,   713,     0,     0,  2468,
  1553,   786,  2635,  2638,     0,     0,     0,     0,   262,     0,
   243,    76,    76,    76,    76,   932,     0,     0,     0,     0,
     0,     0,     0,    76,     0,
    }, yyGindex = {
//yyGindex 194
     0,     0,    18,     0,  -257,     0,    17,    12,  -348,  -226,
     0,     0,     0,  1200,     0,     0,     0,  1069,     0,     0,
   867,  1097,     0,  -270,     0,     0,     0,   579,     0,    26,
  1052,  -273,   -37,     0,    24,     0,   161,   144,     0,    37,
    27,  1286,    14,    39,   657,   187,     1,  -523,     0,   114,
     0,     0,   609,     0,   -15,     0,   -12,  1163,   557,     0,
     0,  -281,   464,  -679,     0,     0,   430,  -488,     0,     0,
     0,   601,   258,  -386,   -78,    -2,  1709,  -426,     0,     0,
   260,   288,    60,     0,     0,   811,   425,   241,     0,     0,
     0,     0,  -162,  2271,   416,  -314,   436,   218,     0,     0,
     0,    72,  -420,     0,  -424,   222,  -259,  -410,     0,  -536,
   310,   -73,   409,  -645,   542,   805,  1191,   -32,   156,   804,
     0,    -8,  -278,     0,  -671,     0,     0,  -214,  -876,     0,
  -393,  -773,   473,   157,     0,  -841,  1132,  -105,  -621,  -276,
     0,     4,     0,  1039,   799,   -62,     0,  -188,  1459,  2040,
     0,     0,     0,   127,   -28,     0,     0,   -26,     0,  -256,
     0,     0,     0,     0,     0,  -227,     0,  -429,     0,     0,
     0,     0,     0,     0,    20,     0,     0,     0,     0,     0,
     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     0,     0,     0,     0,
    };
    protected static final short[] yyTable = YyTables.yyTable();
    protected static final short[] yyCheck = YyTables.yyCheck();

  /** maps symbol value to printable name.
      @see #yyExpecting
    */
  protected static final String[] yyNames = {
    "end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,"' '",null,null,null,null,null,
    null,null,null,null,null,null,"','",null,null,null,null,null,null,
    null,null,null,null,null,null,null,"':'","';'",null,"'='",null,"'?'",
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,
    "'['",null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,
    "keyword_class","keyword_module","keyword_def","keyword_undef",
    "keyword_begin","keyword_rescue","keyword_ensure","keyword_end",
    "keyword_if","keyword_unless","keyword_then","keyword_elsif",
    "keyword_else","keyword_case","keyword_when","keyword_while",
    "keyword_until","keyword_for","keyword_break","keyword_next",
    "keyword_redo","keyword_retry","keyword_in","keyword_do",
    "keyword_do_cond","keyword_do_block","keyword_return","keyword_yield",
    "keyword_super","keyword_self","keyword_nil","keyword_true",
    "keyword_false","keyword_and","keyword_or","keyword_not",
    "modifier_if","modifier_unless","modifier_while","modifier_until",
    "modifier_rescue","keyword_alias","keyword_defined","keyword_BEGIN",
    "keyword_END","keyword__LINE__","keyword__FILE__",
    "keyword__ENCODING__","keyword_do_lambda","tIDENTIFIER","tFID",
    "tGVAR","tIVAR","tCONSTANT","tCVAR","tLABEL","tCHAR","tUPLUS",
    "tUMINUS","tUMINUS_NUM","tPOW","tCMP","tEQ","tEQQ","tNEQ","tGEQ",
    "tLEQ","tANDOP","tOROP","tMATCH","tNMATCH","tDOT","tDOT2","tDOT3",
    "tAREF","tASET","tLSHFT","tRSHFT","tANDDOT","tCOLON2","tCOLON3",
    "tOP_ASGN","tASSOC","tLPAREN","tLPAREN2","tRPAREN","tLPAREN_ARG",
    "tLBRACK","tRBRACK","tLBRACE","tLBRACE_ARG","tSTAR","tSTAR2","tAMPER",
    "tAMPER2","tTILDE","tPERCENT","tDIVIDE","tPLUS","tMINUS","tLT","tGT",
    "tPIPE","tBANG","tCARET","tLCURLY","tRCURLY","tBACK_REF2","tSYMBEG",
    "tSTRING_BEG","tXSTRING_BEG","tREGEXP_BEG","tWORDS_BEG","tQWORDS_BEG",
    "tSTRING_DBEG","tSTRING_DVAR","tSTRING_END","tLAMBDA","tLAMBEG",
    "tNTH_REF","tBACK_REF","tSTRING_CONTENT","tINTEGER","tIMAGINARY",
    "tFLOAT","tRATIONAL","tREGEXP_END","tSYMBOLS_BEG","tQSYMBOLS_BEG",
    "tDSTAR","tSTRING_DEND","tLABEL_END","tLOWEST",
    };

  /** printable rules for debugging.
    */
  protected static final String [] yyRule = {
    "$accept : program",
    "$$1 :",
    "program : $$1 top_compstmt",
    "top_compstmt : top_stmts opt_terms",
    "top_stmts : none",
    "top_stmts : top_stmt",
    "top_stmts : top_stmts terms top_stmt",
    "top_stmts : error top_stmt",
    "top_stmt : stmt",
    "top_stmt : keyword_BEGIN tLCURLY top_compstmt tRCURLY",
    "bodystmt : compstmt opt_rescue opt_else opt_ensure",
    "compstmt : stmts opt_terms",
    "stmts : none",
    "stmts : stmt_or_begin",
    "stmts : stmts terms stmt_or_begin",
    "stmts : error stmt",
    "stmt_or_begin : stmt",
    "$$2 :",
    "stmt_or_begin : keyword_begin $$2 tLCURLY top_compstmt tRCURLY",
    "$$3 :",
    "stmt : keyword_alias fitem $$3 fitem",
    "stmt : keyword_alias tGVAR tGVAR",
    "stmt : keyword_alias tGVAR tBACK_REF",
    "stmt : keyword_alias tGVAR tNTH_REF",
    "stmt : keyword_undef undef_list",
    "stmt : stmt modifier_if expr_value",
    "stmt : stmt modifier_unless expr_value",
    "stmt : stmt modifier_while expr_value",
    "stmt : stmt modifier_until expr_value",
    "stmt : stmt modifier_rescue stmt",
    "stmt : keyword_END tLCURLY compstmt tRCURLY",
    "stmt : command_asgn",
    "stmt : mlhs '=' command_call",
    "stmt : lhs '=' mrhs",
    "stmt : mlhs '=' mrhs_arg",
    "stmt : expr",
    "command_asgn : lhs '=' command_rhs",
    "command_asgn : var_lhs tOP_ASGN command_rhs",
    "command_asgn : primary_value '[' opt_call_args rbracket tOP_ASGN command_rhs",
    "command_asgn : primary_value call_op tIDENTIFIER tOP_ASGN command_rhs",
    "command_asgn : primary_value call_op tCONSTANT tOP_ASGN command_rhs",
    "command_asgn : primary_value tCOLON2 tCONSTANT tOP_ASGN command_rhs",
    "command_asgn : primary_value tCOLON2 tIDENTIFIER tOP_ASGN command_rhs",
    "command_asgn : backref tOP_ASGN command_rhs",
    "command_rhs : command_call",
    "command_rhs : command_call modifier_rescue stmt",
    "command_rhs : command_asgn",
    "expr : command_call",
    "expr : expr keyword_and expr",
    "expr : expr keyword_or expr",
    "expr : keyword_not opt_nl expr",
    "expr : tBANG command_call",
    "expr : arg",
    "expr_value : expr",
    "command_call : command",
    "command_call : block_command",
    "block_command : block_call",
    "block_command : block_call call_op2 operation2 command_args",
    "cmd_brace_block : tLBRACE_ARG brace_body tRCURLY",
    "fcall : operation",
    "command : fcall command_args",
    "command : fcall command_args cmd_brace_block",
    "command : primary_value call_op operation2 command_args",
    "command : primary_value call_op operation2 command_args cmd_brace_block",
    "command : primary_value tCOLON2 operation2 command_args",
    "command : primary_value tCOLON2 operation2 command_args cmd_brace_block",
    "command : keyword_super command_args",
    "command : keyword_yield command_args",
    "command : k_return call_args",
    "command : keyword_break call_args",
    "command : keyword_next call_args",
    "mlhs : mlhs_basic",
    "mlhs : tLPAREN mlhs_inner rparen",
    "mlhs_inner : mlhs_basic",
    "mlhs_inner : tLPAREN mlhs_inner rparen",
    "mlhs_basic : mlhs_head",
    "mlhs_basic : mlhs_head mlhs_item",
    "mlhs_basic : mlhs_head tSTAR mlhs_node",
    "mlhs_basic : mlhs_head tSTAR mlhs_node ',' mlhs_post",
    "mlhs_basic : mlhs_head tSTAR",
    "mlhs_basic : mlhs_head tSTAR ',' mlhs_post",
    "mlhs_basic : tSTAR mlhs_node",
    "mlhs_basic : tSTAR mlhs_node ',' mlhs_post",
    "mlhs_basic : tSTAR",
    "mlhs_basic : tSTAR ',' mlhs_post",
    "mlhs_item : mlhs_node",
    "mlhs_item : tLPAREN mlhs_inner rparen",
    "mlhs_head : mlhs_item ','",
    "mlhs_head : mlhs_head mlhs_item ','",
    "mlhs_post : mlhs_item",
    "mlhs_post : mlhs_post ',' mlhs_item",
    "mlhs_node : tIDENTIFIER",
    "mlhs_node : tIVAR",
    "mlhs_node : tGVAR",
    "mlhs_node : tCONSTANT",
    "mlhs_node : tCVAR",
    "mlhs_node : keyword_nil",
    "mlhs_node : keyword_self",
    "mlhs_node : keyword_true",
    "mlhs_node : keyword_false",
    "mlhs_node : keyword__FILE__",
    "mlhs_node : keyword__LINE__",
    "mlhs_node : keyword__ENCODING__",
    "mlhs_node : primary_value '[' opt_call_args rbracket",
    "mlhs_node : primary_value call_op tIDENTIFIER",
    "mlhs_node : primary_value tCOLON2 tIDENTIFIER",
    "mlhs_node : primary_value call_op tCONSTANT",
    "mlhs_node : primary_value tCOLON2 tCONSTANT",
    "mlhs_node : tCOLON3 tCONSTANT",
    "mlhs_node : backref",
    "lhs : tIDENTIFIER",
    "lhs : tIVAR",
    "lhs : tGVAR",
    "lhs : tCONSTANT",
    "lhs : tCVAR",
    "lhs : keyword_nil",
    "lhs : keyword_self",
    "lhs : keyword_true",
    "lhs : keyword_false",
    "lhs : keyword__FILE__",
    "lhs : keyword__LINE__",
    "lhs : keyword__ENCODING__",
    "lhs : primary_value '[' opt_call_args rbracket",
    "lhs : primary_value call_op tIDENTIFIER",
    "lhs : primary_value tCOLON2 tIDENTIFIER",
    "lhs : primary_value call_op tCONSTANT",
    "lhs : primary_value tCOLON2 tCONSTANT",
    "lhs : tCOLON3 tCONSTANT",
    "lhs : backref",
    "cname : tIDENTIFIER",
    "cname : tCONSTANT",
    "cpath : tCOLON3 cname",
    "cpath : cname",
    "cpath : primary_value tCOLON2 cname",
    "fname : tIDENTIFIER",
    "fname : tCONSTANT",
    "fname : tFID",
    "fname : op",
    "fname : reswords",
    "fsym : fname",
    "fsym : symbol",
    "fitem : fsym",
    "fitem : dsym",
    "undef_list : fitem",
    "$$4 :",
    "undef_list : undef_list ',' $$4 fitem",
    "op : tPIPE",
    "op : tCARET",
    "op : tAMPER2",
    "op : tCMP",
    "op : tEQ",
    "op : tEQQ",
    "op : tMATCH",
    "op : tNMATCH",
    "op : tGT",
    "op : tGEQ",
    "op : tLT",
    "op : tLEQ",
    "op : tNEQ",
    "op : tLSHFT",
    "op : tRSHFT",
    "op : tDSTAR",
    "op : tPLUS",
    "op : tMINUS",
    "op : tSTAR2",
    "op : tSTAR",
    "op : tDIVIDE",
    "op : tPERCENT",
    "op : tPOW",
    "op : tBANG",
    "op : tTILDE",
    "op : tUPLUS",
    "op : tUMINUS",
    "op : tAREF",
    "op : tASET",
    "op : tBACK_REF2",
    "reswords : keyword__LINE__",
    "reswords : keyword__FILE__",
    "reswords : keyword__ENCODING__",
    "reswords : keyword_BEGIN",
    "reswords : keyword_END",
    "reswords : keyword_alias",
    "reswords : keyword_and",
    "reswords : keyword_begin",
    "reswords : keyword_break",
    "reswords : keyword_case",
    "reswords : keyword_class",
    "reswords : keyword_def",
    "reswords : keyword_defined",
    "reswords : keyword_do",
    "reswords : keyword_else",
    "reswords : keyword_elsif",
    "reswords : keyword_end",
    "reswords : keyword_ensure",
    "reswords : keyword_false",
    "reswords : keyword_for",
    "reswords : keyword_in",
    "reswords : keyword_module",
    "reswords : keyword_next",
    "reswords : keyword_nil",
    "reswords : keyword_not",
    "reswords : keyword_or",
    "reswords : keyword_redo",
    "reswords : keyword_rescue",
    "reswords : keyword_retry",
    "reswords : keyword_return",
    "reswords : keyword_self",
    "reswords : keyword_super",
    "reswords : keyword_then",
    "reswords : keyword_true",
    "reswords : keyword_undef",
    "reswords : keyword_when",
    "reswords : keyword_yield",
    "reswords : keyword_if",
    "reswords : keyword_unless",
    "reswords : keyword_while",
    "reswords : keyword_until",
    "reswords : modifier_rescue",
    "arg : lhs '=' arg_rhs",
    "arg : var_lhs tOP_ASGN arg_rhs",
    "arg : primary_value '[' opt_call_args rbracket tOP_ASGN arg",
    "arg : primary_value call_op tIDENTIFIER tOP_ASGN arg_rhs",
    "arg : primary_value call_op tCONSTANT tOP_ASGN arg_rhs",
    "arg : primary_value tCOLON2 tIDENTIFIER tOP_ASGN arg_rhs",
    "arg : primary_value tCOLON2 tCONSTANT tOP_ASGN arg_rhs",
    "arg : tCOLON3 tCONSTANT tOP_ASGN arg_rhs",
    "arg : backref tOP_ASGN arg_rhs",
    "arg : arg tDOT2 arg",
    "arg : arg tDOT3 arg",
    "arg : arg tPLUS arg",
    "arg : arg tMINUS arg",
    "arg : arg tSTAR2 arg",
    "arg : arg tDIVIDE arg",
    "arg : arg tPERCENT arg",
    "arg : arg tPOW arg",
    "arg : tUMINUS_NUM simple_numeric tPOW arg",
    "arg : tUPLUS arg",
    "arg : tUMINUS arg",
    "arg : arg tPIPE arg",
    "arg : arg tCARET arg",
    "arg : arg tAMPER2 arg",
    "arg : arg tCMP arg",
    "arg : rel_expr",
    "arg : arg tEQ arg",
    "arg : arg tEQQ arg",
    "arg : arg tNEQ arg",
    "arg : arg tMATCH arg",
    "arg : arg tNMATCH arg",
    "arg : tBANG arg",
    "arg : tTILDE arg",
    "arg : arg tLSHFT arg",
    "arg : arg tRSHFT arg",
    "arg : arg tANDOP arg",
    "arg : arg tOROP arg",
    "arg : keyword_defined opt_nl arg",
    "arg : arg '?' arg opt_nl ':' arg",
    "arg : primary",
    "relop : tGT",
    "relop : tLT",
    "relop : tGEQ",
    "relop : tLEQ",
    "rel_expr : arg relop arg",
    "rel_expr : rel_expr relop arg",
    "arg_value : arg",
    "aref_args : none",
    "aref_args : args trailer",
    "aref_args : args ',' assocs trailer",
    "aref_args : assocs trailer",
    "arg_rhs : arg",
    "arg_rhs : arg modifier_rescue arg",
    "paren_args : tLPAREN2 opt_call_args rparen",
    "opt_paren_args : none",
    "opt_paren_args : paren_args",
    "opt_call_args : none",
    "opt_call_args : call_args",
    "opt_call_args : args ','",
    "opt_call_args : args ',' assocs ','",
    "opt_call_args : assocs ','",
    "call_args : command",
    "call_args : args opt_block_arg",
    "call_args : assocs opt_block_arg",
    "call_args : args ',' assocs opt_block_arg",
    "call_args : block_arg",
    "$$5 :",
    "command_args : $$5 call_args",
    "block_arg : tAMPER arg_value",
    "opt_block_arg : ',' block_arg",
    "opt_block_arg : none_block_pass",
    "args : arg_value",
    "args : tSTAR arg_value",
    "args : args ',' arg_value",
    "args : args ',' tSTAR arg_value",
    "mrhs_arg : mrhs",
    "mrhs_arg : arg_value",
    "mrhs : args ',' arg_value",
    "mrhs : args ',' tSTAR arg_value",
    "mrhs : tSTAR arg_value",
    "primary : literal",
    "primary : strings",
    "primary : xstring",
    "primary : regexp",
    "primary : words",
    "primary : qwords",
    "primary : symbols",
    "primary : qsymbols",
    "primary : var_ref",
    "primary : backref",
    "primary : tFID",
    "$$6 :",
    "primary : keyword_begin $$6 bodystmt keyword_end",
    "$$7 :",
    "primary : tLPAREN_ARG $$7 rparen",
    "$$8 :",
    "$$9 :",
    "primary : tLPAREN_ARG $$8 stmt $$9 rparen",
    "primary : tLPAREN compstmt tRPAREN",
    "primary : primary_value tCOLON2 tCONSTANT",
    "primary : tCOLON3 tCONSTANT",
    "primary : tLBRACK aref_args tRBRACK",
    "primary : tLBRACE assoc_list tRCURLY",
    "primary : k_return",
    "primary : keyword_yield tLPAREN2 call_args rparen",
    "primary : keyword_yield tLPAREN2 rparen",
    "primary : keyword_yield",
    "primary : keyword_defined opt_nl tLPAREN2 expr rparen",
    "primary : keyword_not tLPAREN2 expr rparen",
    "primary : keyword_not tLPAREN2 rparen",
    "primary : fcall brace_block",
    "primary : method_call",
    "primary : method_call brace_block",
    "primary : tLAMBDA lambda",
    "primary : keyword_if expr_value then compstmt if_tail keyword_end",
    "primary : keyword_unless expr_value then compstmt opt_else keyword_end",
    "$$10 :",
    "$$11 :",
    "primary : keyword_while $$10 expr_value do $$11 compstmt keyword_end",
    "$$12 :",
    "$$13 :",
    "primary : keyword_until $$12 expr_value do $$13 compstmt keyword_end",
    "primary : keyword_case expr_value opt_terms case_body keyword_end",
    "primary : keyword_case opt_terms case_body keyword_end",
    "$$14 :",
    "$$15 :",
    "primary : keyword_for for_var keyword_in $$14 expr_value do $$15 compstmt keyword_end",
    "$$16 :",
    "primary : k_class cpath superclass $$16 bodystmt keyword_end",
    "$$17 :",
    "$$18 :",
    "primary : k_class tLSHFT expr $$17 term $$18 bodystmt keyword_end",
    "$$19 :",
    "primary : k_module cpath $$19 bodystmt keyword_end",
    "$$20 :",
    "$$21 :",
    "primary : keyword_def fname $$20 $$21 f_arglist bodystmt keyword_end",
    "$$22 :",
    "$$23 :",
    "primary : keyword_def singleton dot_or_colon $$22 fname $$23 f_arglist bodystmt keyword_end",
    "primary : keyword_break",
    "primary : keyword_next",
    "primary : keyword_redo",
    "primary : keyword_retry",
    "primary_value : primary",
    "k_class : keyword_class",
    "k_module : keyword_module",
    "k_return : keyword_return",
    "then : term",
    "then : keyword_then",
    "then : term keyword_then",
    "do : term",
    "do : keyword_do_cond",
    "if_tail : opt_else",
    "if_tail : keyword_elsif expr_value then compstmt if_tail",
    "opt_else : none",
    "opt_else : keyword_else compstmt",
    "for_var : lhs",
    "for_var : mlhs",
    "f_marg : f_norm_arg",
    "f_marg : tLPAREN f_margs rparen",
    "f_marg_list : f_marg",
    "f_marg_list : f_marg_list ',' f_marg",
    "f_margs : f_marg_list",
    "f_margs : f_marg_list ',' tSTAR f_norm_arg",
    "f_margs : f_marg_list ',' tSTAR f_norm_arg ',' f_marg_list",
    "f_margs : f_marg_list ',' tSTAR",
    "f_margs : f_marg_list ',' tSTAR ',' f_marg_list",
    "f_margs : tSTAR f_norm_arg",
    "f_margs : tSTAR f_norm_arg ',' f_marg_list",
    "f_margs : tSTAR",
    "f_margs : tSTAR ',' f_marg_list",
    "block_args_tail : f_block_kwarg ',' f_kwrest opt_f_block_arg",
    "block_args_tail : f_block_kwarg opt_f_block_arg",
    "block_args_tail : f_kwrest opt_f_block_arg",
    "block_args_tail : f_block_arg",
    "opt_block_args_tail : ',' block_args_tail",
    "opt_block_args_tail :",
    "block_param : f_arg ',' f_block_optarg ',' f_rest_arg opt_block_args_tail",
    "block_param : f_arg ',' f_block_optarg ',' f_rest_arg ',' f_arg opt_block_args_tail",
    "block_param : f_arg ',' f_block_optarg opt_block_args_tail",
    "block_param : f_arg ',' f_block_optarg ',' f_arg opt_block_args_tail",
    "block_param : f_arg ',' f_rest_arg opt_block_args_tail",
    "block_param : f_arg ','",
    "block_param : f_arg ',' f_rest_arg ',' f_arg opt_block_args_tail",
    "block_param : f_arg opt_block_args_tail",
    "block_param : f_block_optarg ',' f_rest_arg opt_block_args_tail",
    "block_param : f_block_optarg ',' f_rest_arg ',' f_arg opt_block_args_tail",
    "block_param : f_block_optarg opt_block_args_tail",
    "block_param : f_block_optarg ',' f_arg opt_block_args_tail",
    "block_param : f_rest_arg opt_block_args_tail",
    "block_param : f_rest_arg ',' f_arg opt_block_args_tail",
    "block_param : block_args_tail",
    "opt_block_param : none",
    "opt_block_param : block_param_def",
    "block_param_def : tPIPE opt_bv_decl tPIPE",
    "block_param_def : tOROP",
    "block_param_def : tPIPE block_param opt_bv_decl tPIPE",
    "opt_bv_decl : opt_nl",
    "opt_bv_decl : opt_nl ';' bv_decls opt_nl",
    "bv_decls : bvar",
    "bv_decls : bv_decls ',' bvar",
    "bvar : tIDENTIFIER",
    "bvar : f_bad_arg",
    "$$24 :",
    "lambda : $$24 f_larglist lambda_body",
    "f_larglist : tLPAREN2 f_args opt_bv_decl tRPAREN",
    "f_larglist : f_args",
    "lambda_body : tLAMBEG compstmt tRCURLY",
    "lambda_body : keyword_do_lambda compstmt keyword_end",
    "do_block : keyword_do_block do_body keyword_end",
    "block_call : command do_block",
    "block_call : block_call call_op2 operation2 opt_paren_args",
    "block_call : block_call call_op2 operation2 opt_paren_args brace_block",
    "block_call : block_call call_op2 operation2 command_args do_block",
    "method_call : fcall paren_args",
    "method_call : primary_value call_op operation2 opt_paren_args",
    "method_call : primary_value tCOLON2 operation2 paren_args",
    "method_call : primary_value tCOLON2 operation3",
    "method_call : primary_value call_op paren_args",
    "method_call : primary_value tCOLON2 paren_args",
    "method_call : keyword_super paren_args",
    "method_call : keyword_super",
    "method_call : primary_value '[' opt_call_args rbracket",
    "brace_block : tLCURLY brace_body tRCURLY",
    "brace_block : keyword_do do_body keyword_end",
    "$$25 :",
    "brace_body : $$25 opt_block_param compstmt",
    "$$26 :",
    "do_body : $$26 opt_block_param bodystmt",
    "case_body : keyword_when args then compstmt cases",
    "cases : opt_else",
    "cases : case_body",
    "opt_rescue : keyword_rescue exc_list exc_var then compstmt opt_rescue",
    "opt_rescue :",
    "exc_list : arg_value",
    "exc_list : mrhs",
    "exc_list : none",
    "exc_var : tASSOC lhs",
    "exc_var : none",
    "opt_ensure : keyword_ensure compstmt",
    "opt_ensure : none",
    "literal : numeric",
    "literal : symbol",
    "literal : dsym",
    "strings : string",
    "string : tCHAR",
    "string : string1",
    "string : string string1",
    "string1 : tSTRING_BEG string_contents tSTRING_END",
    "xstring : tXSTRING_BEG xstring_contents tSTRING_END",
    "regexp : tREGEXP_BEG regexp_contents tREGEXP_END",
    "words : tWORDS_BEG ' ' word_list tSTRING_END",
    "word_list :",
    "word_list : word_list word ' '",
    "word : string_content",
    "word : word string_content",
    "symbols : tSYMBOLS_BEG ' ' symbol_list tSTRING_END",
    "symbol_list :",
    "symbol_list : symbol_list word ' '",
    "qwords : tQWORDS_BEG ' ' qword_list tSTRING_END",
    "qsymbols : tQSYMBOLS_BEG ' ' qsym_list tSTRING_END",
    "qword_list :",
    "qword_list : qword_list tSTRING_CONTENT ' '",
    "qsym_list :",
    "qsym_list : qsym_list tSTRING_CONTENT ' '",
    "string_contents :",
    "string_contents : string_contents string_content",
    "xstring_contents :",
    "xstring_contents : xstring_contents string_content",
    "regexp_contents :",
    "regexp_contents : regexp_contents string_content",
    "string_content : tSTRING_CONTENT",
    "$$27 :",
    "string_content : tSTRING_DVAR $$27 string_dvar",
    "$$28 :",
    "$$29 :",
    "$$30 :",
    "$$31 :",
    "$$32 :",
    "string_content : tSTRING_DBEG $$28 $$29 $$30 $$31 $$32 compstmt tSTRING_DEND",
    "string_dvar : tGVAR",
    "string_dvar : tIVAR",
    "string_dvar : tCVAR",
    "string_dvar : backref",
    "symbol : tSYMBEG sym",
    "sym : fname",
    "sym : tIVAR",
    "sym : tGVAR",
    "sym : tCVAR",
    "dsym : tSYMBEG xstring_contents tSTRING_END",
    "numeric : simple_numeric",
    "numeric : tUMINUS_NUM simple_numeric",
    "simple_numeric : tINTEGER",
    "simple_numeric : tFLOAT",
    "simple_numeric : tRATIONAL",
    "simple_numeric : tIMAGINARY",
    "var_ref : tIDENTIFIER",
    "var_ref : tIVAR",
    "var_ref : tGVAR",
    "var_ref : tCONSTANT",
    "var_ref : tCVAR",
    "var_ref : keyword_nil",
    "var_ref : keyword_self",
    "var_ref : keyword_true",
    "var_ref : keyword_false",
    "var_ref : keyword__FILE__",
    "var_ref : keyword__LINE__",
    "var_ref : keyword__ENCODING__",
    "var_lhs : tIDENTIFIER",
    "var_lhs : tIVAR",
    "var_lhs : tGVAR",
    "var_lhs : tCONSTANT",
    "var_lhs : tCVAR",
    "var_lhs : keyword_nil",
    "var_lhs : keyword_self",
    "var_lhs : keyword_true",
    "var_lhs : keyword_false",
    "var_lhs : keyword__FILE__",
    "var_lhs : keyword__LINE__",
    "var_lhs : keyword__ENCODING__",
    "backref : tNTH_REF",
    "backref : tBACK_REF",
    "$$33 :",
    "superclass : tLT $$33 expr_value term",
    "superclass :",
    "f_arglist : tLPAREN2 f_args rparen",
    "$$34 :",
    "f_arglist : $$34 f_args term",
    "args_tail : f_kwarg ',' f_kwrest opt_f_block_arg",
    "args_tail : f_kwarg opt_f_block_arg",
    "args_tail : f_kwrest opt_f_block_arg",
    "args_tail : f_block_arg",
    "opt_args_tail : ',' args_tail",
    "opt_args_tail :",
    "f_args : f_arg ',' f_optarg ',' f_rest_arg opt_args_tail",
    "f_args : f_arg ',' f_optarg ',' f_rest_arg ',' f_arg opt_args_tail",
    "f_args : f_arg ',' f_optarg opt_args_tail",
    "f_args : f_arg ',' f_optarg ',' f_arg opt_args_tail",
    "f_args : f_arg ',' f_rest_arg opt_args_tail",
    "f_args : f_arg ',' f_rest_arg ',' f_arg opt_args_tail",
    "f_args : f_arg opt_args_tail",
    "f_args : f_optarg ',' f_rest_arg opt_args_tail",
    "f_args : f_optarg ',' f_rest_arg ',' f_arg opt_args_tail",
    "f_args : f_optarg opt_args_tail",
    "f_args : f_optarg ',' f_arg opt_args_tail",
    "f_args : f_rest_arg opt_args_tail",
    "f_args : f_rest_arg ',' f_arg opt_args_tail",
    "f_args : args_tail",
    "f_args :",
    "f_bad_arg : tCONSTANT",
    "f_bad_arg : tIVAR",
    "f_bad_arg : tGVAR",
    "f_bad_arg : tCVAR",
    "f_norm_arg : f_bad_arg",
    "f_norm_arg : tIDENTIFIER",
    "f_arg_asgn : f_norm_arg",
    "f_arg_item : f_arg_asgn",
    "f_arg_item : tLPAREN f_margs rparen",
    "f_arg : f_arg_item",
    "f_arg : f_arg ',' f_arg_item",
    "f_label : tLABEL",
    "f_kw : f_label arg_value",
    "f_kw : f_label",
    "f_block_kw : f_label primary_value",
    "f_block_kw : f_label",
    "f_block_kwarg : f_block_kw",
    "f_block_kwarg : f_block_kwarg ',' f_block_kw",
    "f_kwarg : f_kw",
    "f_kwarg : f_kwarg ',' f_kw",
    "kwrest_mark : tPOW",
    "kwrest_mark : tDSTAR",
    "f_kwrest : kwrest_mark tIDENTIFIER",
    "f_kwrest : kwrest_mark",
    "f_opt : f_arg_asgn '=' arg_value",
    "f_block_opt : f_arg_asgn '=' primary_value",
    "f_block_optarg : f_block_opt",
    "f_block_optarg : f_block_optarg ',' f_block_opt",
    "f_optarg : f_opt",
    "f_optarg : f_optarg ',' f_opt",
    "restarg_mark : tSTAR2",
    "restarg_mark : tSTAR",
    "f_rest_arg : restarg_mark tIDENTIFIER",
    "f_rest_arg : restarg_mark",
    "blkarg_mark : tAMPER2",
    "blkarg_mark : tAMPER",
    "f_block_arg : blkarg_mark tIDENTIFIER",
    "opt_f_block_arg : ',' f_block_arg",
    "opt_f_block_arg :",
    "singleton : var_ref",
    "$$35 :",
    "singleton : tLPAREN2 $$35 expr rparen",
    "assoc_list : none",
    "assoc_list : assocs trailer",
    "assocs : assoc",
    "assocs : assocs ',' assoc",
    "assoc : arg_value tASSOC arg_value",
    "assoc : tLABEL arg_value",
    "assoc : tSTRING_BEG string_contents tLABEL_END arg_value",
    "assoc : tDSTAR arg_value",
    "operation : tIDENTIFIER",
    "operation : tCONSTANT",
    "operation : tFID",
    "operation2 : tIDENTIFIER",
    "operation2 : tCONSTANT",
    "operation2 : tFID",
    "operation2 : op",
    "operation3 : tIDENTIFIER",
    "operation3 : tFID",
    "operation3 : op",
    "dot_or_colon : tDOT",
    "dot_or_colon : tCOLON2",
    "call_op : tDOT",
    "call_op : tANDDOT",
    "call_op2 : call_op",
    "call_op2 : tCOLON2",
    "opt_terms :",
    "opt_terms : terms",
    "opt_nl :",
    "opt_nl : '\\n'",
    "rparen : opt_nl tRPAREN",
    "rbracket : opt_nl tRBRACK",
    "trailer :",
    "trailer : '\\n'",
    "trailer : ','",
    "term : ';'",
    "term : '\\n'",
    "terms : term",
    "terms : terms ';'",
    "none :",
    "none_block_pass :",
    };

  protected org.jruby.parser.YYDebug yydebug;

  /** index-checked interface to {@link #yyNames}.
      @param token single character or <tt>%token</tt> value.
      @return token name or <tt>[illegal]</tt> or <tt>[unknown]</tt>.
    */
  public static String yyName (int token) {
    if (token < 0 || token > yyNames.length) return "[illegal]";
    String name;
    if ((name = yyNames[token]) != null) return name;
    return "[unknown]";
  }


  /** computes list of expected tokens on error by tracing the tables.
      @param state for which to compute the list.
      @return list of token names.
    */
  protected String[] yyExpecting (int state) {
    int token, n, len = 0;
    boolean[] ok = new boolean[yyNames.length];

    if ((n = yySindex[state]) != 0)
      for (token = n < 0 ? -n : 0;
           token < yyNames.length && n+token < yyTable.length; ++ token)
        if (yyCheck[n+token] == token && !ok[token] && yyNames[token] != null) {
          ++ len;
          ok[token] = true;
        }
    if ((n = yyRindex[state]) != 0)
      for (token = n < 0 ? -n : 0;
           token < yyNames.length && n+token < yyTable.length; ++ token)
        if (yyCheck[n+token] == token && !ok[token] && yyNames[token] != null) {
          ++ len;
          ok[token] = true;
        }

    String result[] = new String[len];
    for (n = token = 0; n < len;  ++ token)
      if (ok[token]) result[n++] = yyNames[token];
    return result;
  }

  /** the generated parser, with debugging messages.
      Maintains a dynamic state and value stack.
      @param yyLex scanner.
      @param ayydebug debug message writer implementing <tt>yyDebug</tt>, or <tt>null</tt>.
      @return result of the last reduction, if any.
    */
  public Object yyparse (RubyLexer yyLex, Object ayydebug)
				throws java.io.IOException {
    this.yydebug = (org.jruby.parser.YYDebug) ayydebug;
    return yyparse(yyLex);
  }

  /** initial size and increment of the state/value stack [default 256].
      This is not final so that it can be overwritten outside of invocations
      of {@link #yyparse}.
    */
  protected int yyMax;

  /** executed at the beginning of a reduce action.
      Used as <tt>$$ = yyDefault($1)</tt>, prior to the user-specified action, if any.
      Can be overwritten to provide deep copy, etc.
      @param first value for <tt>$1</tt>, or <tt>null</tt>.
      @return first.
    */
  protected Object yyDefault (Object first) {
    return first;
  }

  /** the generated parser.
      Maintains a dynamic state and value stack.
      @param yyLex scanner.
      @return result of the last reduction, if any.
    */
  public Object yyparse (RubyLexer yyLex) throws java.io.IOException {
    if (yyMax <= 0) yyMax = 256;			// initial size
    int yyState = 0, yyStates[] = new int[yyMax];	// state stack
    Object yyVal = null, yyVals[] = new Object[yyMax];	// value stack
    int yyToken = -1;					// current input
    int yyErrorFlag = 0;				// #tokens to shift

    yyLoop: for (int yyTop = 0;; ++ yyTop) {
      if (yyTop >= yyStates.length) {			// dynamically increase
        int[] i = new int[yyStates.length+yyMax];
        System.arraycopy(yyStates, 0, i, 0, yyStates.length);
        yyStates = i;
        Object[] o = new Object[yyVals.length+yyMax];
        System.arraycopy(yyVals, 0, o, 0, yyVals.length);
        yyVals = o;
      }
      yyStates[yyTop] = yyState;
      yyVals[yyTop] = yyVal;
      if (yydebug != null) yydebug.push(yyState, yyVal);

      yyDiscarded: for (;;) {	// discarding a token does not change stack
        int yyN;
        if ((yyN = yyDefRed[yyState]) == 0) {	// else [default] reduce (yyN)
          if (yyToken < 0) {
//            yyToken = yyLex.advance() ? yyLex.token() : 0;
            yyToken = yyLex.nextToken();
            if (yydebug != null)
              yydebug.lex(yyState, yyToken, yyName(yyToken), yyLex.value());
          }
          if ((yyN = yySindex[yyState]) != 0 && (yyN += yyToken) >= 0
              && yyN < yyTable.length && yyCheck[yyN] == yyToken) {
            if (yydebug != null)
              yydebug.shift(yyState, yyTable[yyN], yyErrorFlag-1);
            yyState = yyTable[yyN];		// shift to yyN
            yyVal = yyLex.value();
            yyToken = -1;
            if (yyErrorFlag > 0) -- yyErrorFlag;
            continue yyLoop;
          }
          if ((yyN = yyRindex[yyState]) != 0 && (yyN += yyToken) >= 0
              && yyN < yyTable.length && yyCheck[yyN] == yyToken)
            yyN = yyTable[yyN];			// reduce (yyN)
          else
            switch (yyErrorFlag) {
  
            case 0:
              support.yyerror("syntax error", yyExpecting(yyState), yyNames[yyToken]);
              if (yydebug != null) yydebug.error("syntax error");
  
            case 1: case 2:
              yyErrorFlag = 3;
              do {
                if ((yyN = yySindex[yyStates[yyTop]]) != 0
                    && (yyN += yyErrorCode) >= 0 && yyN < yyTable.length
                    && yyCheck[yyN] == yyErrorCode) {
                  if (yydebug != null)
                    yydebug.shift(yyStates[yyTop], yyTable[yyN], 3);
                  yyState = yyTable[yyN];
                  yyVal = yyLex.value();
                  continue yyLoop;
                }
                if (yydebug != null) yydebug.pop(yyStates[yyTop]);
              } while (-- yyTop >= 0);
              if (yydebug != null) yydebug.reject();
              support.yyerror("irrecoverable syntax error");
  
            case 3:
              if (yyToken == 0) {
                if (yydebug != null) yydebug.reject();
                support.yyerror("irrecoverable syntax error at end-of-file");
              }
              if (yydebug != null)
                yydebug.discard(yyState, yyToken, yyName(yyToken),
  							yyLex.value());
              yyToken = -1;
              continue yyDiscarded;		// leave stack alone
            }
        }
        int yyV = yyTop + 1-yyLen[yyN];
        if (yydebug != null)
          yydebug.reduce(yyState, yyStates[yyV-1], yyN, yyRule[yyN], yyLen[yyN]);
        ParserState state = states[yyN];
        if (state == null) {
            yyVal = yyDefault(yyV > yyTop ? null : yyVals[yyV]);
        } else {
            yyVal = state.execute(support, lexer, yyVal, yyVals, yyTop);
        }
//        switch (yyN) {
// ACTIONS_END
//        }
        yyTop -= yyLen[yyN];
        yyState = yyStates[yyTop];
        int yyM = yyLhs[yyN];
        if (yyState == 0 && yyM == 0) {
          if (yydebug != null) yydebug.shift(0, yyFinal);
          yyState = yyFinal;
          if (yyToken < 0) {
            yyToken = yyLex.nextToken();
//            yyToken = yyLex.advance() ? yyLex.token() : 0;
            if (yydebug != null)
               yydebug.lex(yyState, yyToken,yyName(yyToken), yyLex.value());
          }
          if (yyToken == 0) {
            if (yydebug != null) yydebug.accept(yyVal);
            return yyVal;
          }
          continue yyLoop;
        }
        if ((yyN = yyGindex[yyM]) != 0 && (yyN += yyState) >= 0
            && yyN < yyTable.length && yyCheck[yyN] == yyState)
          yyState = yyTable[yyN];
        else
          yyState = yyDgoto[yyM];
        if (yydebug != null) yydebug.shift(yyStates[yyTop], yyState);
        continue yyLoop;
      }
    }
  }

static ParserState[] states = new ParserState[648];
static {
states[1] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                  lexer.setState(EXPR_BEG);
                  support.initTopLocalVariables();
    return yyVal;
  }
};
states[2] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
  /* ENEBO: Removed !compile_for_eval which probably is to reduce warnings*/
                  if (((Node)yyVals[0+yyTop]) != null) {
                      /* last expression should not be void */
                      if (((Node)yyVals[0+yyTop]) instanceof BlockNode) {
                          support.checkUselessStatement(((BlockNode)yyVals[0+yyTop]).getLast());
                      } else {
                          support.checkUselessStatement(((Node)yyVals[0+yyTop]));
                      }
                  }
                  support.getResult().setAST(support.addRootNode(((Node)yyVals[0+yyTop])));
    return yyVal;
  }
};
states[3] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                  if (((Node)yyVals[-1+yyTop]) instanceof BlockNode) {
                      support.checkUselessStatements(((BlockNode)yyVals[-1+yyTop]));
                  }
                  yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[5] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newline_node(((Node)yyVals[0+yyTop]), support.getPosition(((Node)yyVals[0+yyTop])));
    return yyVal;
  }
};
states[6] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.appendToBlock(((Node)yyVals[-2+yyTop]), support.newline_node(((Node)yyVals[0+yyTop]), support.getPosition(((Node)yyVals[0+yyTop]))));
    return yyVal;
  }
};
states[7] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[9] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.getResult().addBeginNode(new PreExe19Node(((ISourcePosition)yyVals[-3+yyTop]), support.getCurrentScope(), ((Node)yyVals[-1+yyTop])));
                    yyVal = null;
    return yyVal;
  }
};
states[10] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                  Node node = ((Node)yyVals[-3+yyTop]);

                  if (((RescueBodyNode)yyVals[-2+yyTop]) != null) {
                      node = new RescueNode(support.getPosition(((Node)yyVals[-3+yyTop])), ((Node)yyVals[-3+yyTop]), ((RescueBodyNode)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]));
                  } else if (((Node)yyVals[-1+yyTop]) != null) {
                      support.warn(ID.ELSE_WITHOUT_RESCUE, support.getPosition(((Node)yyVals[-3+yyTop])), "else without rescue is useless");
                      node = support.appendToBlock(((Node)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]));
                  }
                  if (((Node)yyVals[0+yyTop]) != null) {
                      if (node != null) {
                          node = new EnsureNode(support.getPosition(((Node)yyVals[-3+yyTop])), support.makeNullNil(node), ((Node)yyVals[0+yyTop]));
                      } else {
                          node = support.appendToBlock(((Node)yyVals[0+yyTop]), NilImplicitNode.NIL);
                      }
                  }

                  support.fixpos(node, ((Node)yyVals[-3+yyTop]));
                  yyVal = node;
    return yyVal;
  }
};
states[11] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-1+yyTop]) instanceof BlockNode) {
                        support.checkUselessStatements(((BlockNode)yyVals[-1+yyTop]));
                    }
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[13] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newline_node(((Node)yyVals[0+yyTop]), support.getPosition(((Node)yyVals[0+yyTop])));
    return yyVal;
  }
};
states[14] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.appendToBlock(((Node)yyVals[-2+yyTop]), support.newline_node(((Node)yyVals[0+yyTop]), support.getPosition(((Node)yyVals[0+yyTop]))));
    return yyVal;
  }
};
states[15] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[16] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[17] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   support.yyerror("BEGIN is permitted only at toplevel");
    return yyVal;
  }
};
states[18] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BeginNode(((ISourcePosition)yyVals[-4+yyTop]), support.makeNullNil(((Node)yyVals[-3+yyTop])));
    return yyVal;
  }
};
states[19] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setState(EXPR_FNAME|EXPR_FITEM);
    return yyVal;
  }
};
states[20] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newAlias(((ISourcePosition)yyVals[-3+yyTop]), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[21] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new VAliasNode(((ISourcePosition)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[22] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new VAliasNode(((ISourcePosition)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((BackRefNode)yyVals[0+yyTop]).getByteName());
    return yyVal;
  }
};
states[23] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.yyerror("can't make alias for the number variables");
    return yyVal;
  }
};
states[24] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[25] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IfNode(support.getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), ((Node)yyVals[-2+yyTop]), null);
                    support.fixpos(((Node)yyVal), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[26] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IfNode(support.getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), null, ((Node)yyVals[-2+yyTop]));
                    support.fixpos(((Node)yyVal), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[27] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-2+yyTop]) != null && ((Node)yyVals[-2+yyTop]) instanceof BeginNode) {
                        yyVal = new WhileNode(support.getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), ((BeginNode)yyVals[-2+yyTop]).getBodyNode(), false);
                    } else {
                        yyVal = new WhileNode(support.getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), ((Node)yyVals[-2+yyTop]), true);
                    }
    return yyVal;
  }
};
states[28] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-2+yyTop]) != null && ((Node)yyVals[-2+yyTop]) instanceof BeginNode) {
                        yyVal = new UntilNode(support.getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), ((BeginNode)yyVals[-2+yyTop]).getBodyNode(), false);
                    } else {
                        yyVal = new UntilNode(support.getPosition(((Node)yyVals[-2+yyTop])), support.getConditionNode(((Node)yyVals[0+yyTop])), ((Node)yyVals[-2+yyTop]), true);
                    }
    return yyVal;
  }
};
states[29] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newRescueModNode(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[30] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        support.warn(ID.END_IN_METHOD, ((ISourcePosition)yyVals[-3+yyTop]), "END in method; use at_exit");
                    }
                    yyVal = new PostExeNode(((ISourcePosition)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[32] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    ((MultipleAsgnNode)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                    yyVal = ((MultipleAsgnNode)yyVals[-2+yyTop]);
    return yyVal;
  }
};
states[33] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = support.node_assign(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[34] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    ((AssignableNode)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                    yyVal = ((MultipleAsgnNode)yyVals[-2+yyTop]);
                    ((MultipleAsgnNode)yyVals[-2+yyTop]).setPosition(support.getPosition(((MultipleAsgnNode)yyVals[-2+yyTop])));
    return yyVal;
  }
};
states[36] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = support.node_assign(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[37] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));

                    ISourcePosition pos = ((AssignableNode)yyVals[-2+yyTop]).getPosition();
                    ByteList asgnOp = ((ByteList)yyVals[-1+yyTop]);
                    if (asgnOp == lexer.OR_OR) {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                        yyVal = new OpAsgnOrNode(pos, support.gettable2(((AssignableNode)yyVals[-2+yyTop])), ((AssignableNode)yyVals[-2+yyTop]));
                    } else if (asgnOp == lexer.AMPERSAND_AMPERSAND) {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                        yyVal = new OpAsgnAndNode(pos, support.gettable2(((AssignableNode)yyVals[-2+yyTop])), ((AssignableNode)yyVals[-2+yyTop]));
                    } else {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(support.getOperatorCallNode(support.gettable2(((AssignableNode)yyVals[-2+yyTop])), asgnOp, ((Node)yyVals[0+yyTop])));
                        ((AssignableNode)yyVals[-2+yyTop]).setPosition(pos);
                        yyVal = ((AssignableNode)yyVals[-2+yyTop]);
                    }
    return yyVal;
  }
};
states[38] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
  /* FIXME: arg_concat logic missing for opt_call_args*/
                    yyVal = support.new_opElementAsgnNode(((Node)yyVals[-5+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[39] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = support.newOpAsgn(support.getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[40] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = support.newOpAsgn(support.getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[41] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    ISourcePosition pos = ((Node)yyVals[-4+yyTop]).getPosition();
                    yyVal = support.newOpConstAsgn(pos, support.new_colon2(pos, ((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-3+yyTop])), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[42] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = support.newOpAsgn(support.getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[43] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.backrefAssignError(((Node)yyVals[-2+yyTop]));
    return yyVal;
  }
};
states[44] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[45] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[-2+yyTop]));
                    yyVal = support.newRescueModNode(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[48] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newAndNode(support.getPosition(((Node)yyVals[-2+yyTop])), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[49] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newOrNode(support.getPosition(((Node)yyVals[-2+yyTop])), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[50] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(support.getConditionNode(((Node)yyVals[0+yyTop])), lexer.BANG);
    return yyVal;
  }
};
states[51] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(support.getConditionNode(((Node)yyVals[0+yyTop])), ((ByteList)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[53] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[57] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[58] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((IterNode)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[59] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_fcall(((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[60] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.frobnicate_fcall_args(((FCallNode)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
                    yyVal = ((FCallNode)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[61] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.frobnicate_fcall_args(((FCallNode)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), ((IterNode)yyVals[0+yyTop]));
                    yyVal = ((FCallNode)yyVals[-2+yyTop]);
    return yyVal;
  }
};
states[62] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[63] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-3+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), ((IterNode)yyVals[0+yyTop])); 
    return yyVal;
  }
};
states[64] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[65] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), ((IterNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[66] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_super(((ISourcePosition)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[67] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_yield(((ISourcePosition)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[68] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ReturnNode(((ISourcePosition)yyVals[-1+yyTop]), support.ret_args(((Node)yyVals[0+yyTop]), ((ISourcePosition)yyVals[-1+yyTop])));
    return yyVal;
  }
};
states[69] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BreakNode(((ISourcePosition)yyVals[-1+yyTop]), support.ret_args(((Node)yyVals[0+yyTop]), ((ISourcePosition)yyVals[-1+yyTop])));
    return yyVal;
  }
};
states[70] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new NextNode(((ISourcePosition)yyVals[-1+yyTop]), support.ret_args(((Node)yyVals[0+yyTop]), ((ISourcePosition)yyVals[-1+yyTop])));
    return yyVal;
  }
};
states[72] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[73] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((MultipleAsgnNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[74] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ISourcePosition)yyVals[-2+yyTop]), support.newArrayNode(((ISourcePosition)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop])), null, null);
    return yyVal;
  }
};
states[75] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ListNode)yyVals[0+yyTop]).getPosition(), ((ListNode)yyVals[0+yyTop]), null, null);
    return yyVal;
  }
};
states[76] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ListNode)yyVals[-1+yyTop]).getPosition(), ((ListNode)yyVals[-1+yyTop]).add(((Node)yyVals[0+yyTop])), null, null);
    return yyVal;
  }
};
states[77] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ListNode)yyVals[-2+yyTop]).getPosition(), ((ListNode)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]), (ListNode) null);
    return yyVal;
  }
};
states[78] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ListNode)yyVals[-4+yyTop]).getPosition(), ((ListNode)yyVals[-4+yyTop]), ((Node)yyVals[-2+yyTop]), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[79] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ListNode)yyVals[-1+yyTop]).getPosition(), ((ListNode)yyVals[-1+yyTop]), new StarNode(lexer.getPosition()), null);
    return yyVal;
  }
};
states[80] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ListNode)yyVals[-3+yyTop]).getPosition(), ((ListNode)yyVals[-3+yyTop]), new StarNode(lexer.getPosition()), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[81] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((Node)yyVals[0+yyTop]).getPosition(), null, ((Node)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[82] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((Node)yyVals[-2+yyTop]).getPosition(), null, ((Node)yyVals[-2+yyTop]), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[83] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                      yyVal = new MultipleAsgnNode(lexer.getPosition(), null, new StarNode(lexer.getPosition()), null);
    return yyVal;
  }
};
states[84] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                      yyVal = new MultipleAsgnNode(lexer.getPosition(), null, new StarNode(lexer.getPosition()), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[86] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[87] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(((Node)yyVals[-1+yyTop]).getPosition(), ((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[88] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[89] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(((Node)yyVals[0+yyTop]).getPosition(), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[90] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[91] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.assignableLabelOrIdentifier(((ByteList)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[92] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = new InstAsgnNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[93] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = new GlobalAsgnNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[94] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) support.compile_error("dynamic constant assignment");

                    yyVal = new ConstDeclNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), null, NilImplicitNode.NIL);
    return yyVal;
  }
};
states[95] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ClassVarAsgnNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[96] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to nil");
                    yyVal = null;
    return yyVal;
  }
};
states[97] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't change the value of self");
                    yyVal = null;
    return yyVal;
  }
};
states[98] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to true");
                    yyVal = null;
    return yyVal;
  }
};
states[99] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to false");
                    yyVal = null;
    return yyVal;
  }
};
states[100] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to __FILE__");
                    yyVal = null;
    return yyVal;
  }
};
states[101] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to __LINE__");
                    yyVal = null;
    return yyVal;
  }
};
states[102] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to __ENCODING__");
                    yyVal = null;
    return yyVal;
  }
};
states[103] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.aryset(((Node)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[104] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[105] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[106] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[107] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        support.yyerror("dynamic constant assignment");
                    }

                    ISourcePosition position = support.getPosition(((Node)yyVals[-2+yyTop]));

                    yyVal = new ConstDeclNode(position, (ByteList) null, support.new_colon2(position, ((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[0+yyTop])), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[108] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        support.yyerror("dynamic constant assignment");
                    }

                    ISourcePosition position = lexer.tokline;

                    yyVal = new ConstDeclNode(position, (ByteList) null, support.new_colon3(position, ((ByteList)yyVals[0+yyTop])), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[109] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.backrefAssignError(((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[110] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.assignableLabelOrIdentifier(((ByteList)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[111] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = new InstAsgnNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[112] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = new GlobalAsgnNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[113] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) support.compile_error("dynamic constant assignment");

                    yyVal = new ConstDeclNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), null, NilImplicitNode.NIL);
    return yyVal;
  }
};
states[114] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ClassVarAsgnNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[115] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to nil");
                    yyVal = null;
    return yyVal;
  }
};
states[116] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't change the value of self");
                    yyVal = null;
    return yyVal;
  }
};
states[117] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to true");
                    yyVal = null;
    return yyVal;
  }
};
states[118] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to false");
                    yyVal = null;
    return yyVal;
  }
};
states[119] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to __FILE__");
                    yyVal = null;
    return yyVal;
  }
};
states[120] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to __LINE__");
                    yyVal = null;
    return yyVal;
  }
};
states[121] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to __ENCODING__");
                    yyVal = null;
    return yyVal;
  }
};
states[122] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.aryset(((Node)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[123] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[124] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[125] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.attrset(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[126] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        support.yyerror("dynamic constant assignment");
                    }

                    ISourcePosition position = support.getPosition(((Node)yyVals[-2+yyTop]));

                    yyVal = new ConstDeclNode(position, (ByteList) null, support.new_colon2(position, ((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[0+yyTop])), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[127] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) {
                        support.yyerror("dynamic constant assignment");
                    }

                    ISourcePosition position = lexer.tokline;

                    yyVal = new ConstDeclNode(position, (ByteList) null, support.new_colon3(position, ((ByteList)yyVals[0+yyTop])), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[128] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.backrefAssignError(((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[129] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.yyerror("class/module name must be CONSTANT");
    return yyVal;
  }
};
states[130] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[131] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_colon3(lexer.tokline, ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[132] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_colon2(lexer.tokline, null, ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[133] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_colon2(support.getPosition(((Node)yyVals[-2+yyTop])), ((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[134] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[135] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[136] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[137] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   lexer.setState(EXPR_ENDFN);
                   yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[138] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   lexer.setState(EXPR_ENDFN);
                   yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[139] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = new LiteralNode(lexer.getPosition(), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[140] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = new LiteralNode(lexer.getPosition(), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[141] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((LiteralNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[142] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[143] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newUndef(((Node)yyVals[0+yyTop]).getPosition(), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[144] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setState(EXPR_FNAME|EXPR_FITEM);
    return yyVal;
  }
};
states[145] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.appendToBlock(((Node)yyVals[-3+yyTop]), support.newUndef(((Node)yyVals[-3+yyTop]).getPosition(), ((Node)yyVals[0+yyTop])));
    return yyVal;
  }
};
states[146] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[147] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[148] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[149] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[150] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[151] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[152] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[153] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[154] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[155] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[156] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[157] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[158] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[159] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[160] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[161] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[162] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[163] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[164] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[165] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[166] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[167] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[168] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[169] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[170] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[171] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[172] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[173] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[174] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[175] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[176] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.__LINE__.bytes;
    return yyVal;
  }
};
states[177] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.__FILE__.bytes;
    return yyVal;
  }
};
states[178] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.__ENCODING__.bytes;
    return yyVal;
  }
};
states[179] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.LBEGIN.bytes;
    return yyVal;
  }
};
states[180] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.LEND.bytes;
    return yyVal;
  }
};
states[181] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.ALIAS.bytes;
    return yyVal;
  }
};
states[182] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.AND.bytes;
    return yyVal;
  }
};
states[183] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.BEGIN.bytes;
    return yyVal;
  }
};
states[184] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.BREAK.bytes;
    return yyVal;
  }
};
states[185] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.CASE.bytes;
    return yyVal;
  }
};
states[186] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.CLASS.bytes;
    return yyVal;
  }
};
states[187] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.DEF.bytes;
    return yyVal;
  }
};
states[188] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.DEFINED_P.bytes;
    return yyVal;
  }
};
states[189] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.DO.bytes;
    return yyVal;
  }
};
states[190] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.ELSE.bytes;
    return yyVal;
  }
};
states[191] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.ELSIF.bytes;
    return yyVal;
  }
};
states[192] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.END.bytes;
    return yyVal;
  }
};
states[193] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.ENSURE.bytes;
    return yyVal;
  }
};
states[194] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.FALSE.bytes;
    return yyVal;
  }
};
states[195] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.FOR.bytes;
    return yyVal;
  }
};
states[196] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.IN.bytes;
    return yyVal;
  }
};
states[197] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.MODULE.bytes;
    return yyVal;
  }
};
states[198] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.NEXT.bytes;
    return yyVal;
  }
};
states[199] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.NIL.bytes;
    return yyVal;
  }
};
states[200] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.NOT.bytes;
    return yyVal;
  }
};
states[201] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.OR.bytes;
    return yyVal;
  }
};
states[202] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.REDO.bytes;
    return yyVal;
  }
};
states[203] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.RESCUE.bytes;
    return yyVal;
  }
};
states[204] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.RETRY.bytes;
    return yyVal;
  }
};
states[205] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.RETURN.bytes;
    return yyVal;
  }
};
states[206] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.SELF.bytes;
    return yyVal;
  }
};
states[207] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.SUPER.bytes;
    return yyVal;
  }
};
states[208] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.THEN.bytes;
    return yyVal;
  }
};
states[209] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.TRUE.bytes;
    return yyVal;
  }
};
states[210] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.UNDEF.bytes;
    return yyVal;
  }
};
states[211] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.WHEN.bytes;
    return yyVal;
  }
};
states[212] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.YIELD.bytes;
    return yyVal;
  }
};
states[213] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.IF.bytes;
    return yyVal;
  }
};
states[214] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.UNLESS.bytes;
    return yyVal;
  }
};
states[215] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.WHILE.bytes;
    return yyVal;
  }
};
states[216] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.UNTIL.bytes;
    return yyVal;
  }
};
states[217] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = RubyLexer.Keyword.RESCUE.bytes;
    return yyVal;
  }
};
states[218] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.node_assign(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
                    /* FIXME: Consider fixing node_assign itself rather than single case*/
                    ((Node)yyVal).setPosition(support.getPosition(((Node)yyVals[-2+yyTop])));
    return yyVal;
  }
};
states[219] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));

                    ISourcePosition pos = ((AssignableNode)yyVals[-2+yyTop]).getPosition();
                    ByteList asgnOp = ((ByteList)yyVals[-1+yyTop]);
                    if (asgnOp == lexer.OR_OR) {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                        yyVal = new OpAsgnOrNode(pos, support.gettable2(((AssignableNode)yyVals[-2+yyTop])), ((AssignableNode)yyVals[-2+yyTop]));
                    } else if (asgnOp == lexer.AMPERSAND_AMPERSAND) {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(((Node)yyVals[0+yyTop]));
                        yyVal = new OpAsgnAndNode(pos, support.gettable2(((AssignableNode)yyVals[-2+yyTop])), ((AssignableNode)yyVals[-2+yyTop]));
                    } else {
                        ((AssignableNode)yyVals[-2+yyTop]).setValueNode(support.getOperatorCallNode(support.gettable2(((AssignableNode)yyVals[-2+yyTop])), asgnOp, ((Node)yyVals[0+yyTop])));
                        ((AssignableNode)yyVals[-2+yyTop]).setPosition(pos);
                        yyVal = ((AssignableNode)yyVals[-2+yyTop]);
                    }
    return yyVal;
  }
};
states[220] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
  /* FIXME: arg_concat missing for opt_call_args*/
                    yyVal = support.new_opElementAsgnNode(((Node)yyVals[-5+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[221] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = support.newOpAsgn(support.getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[222] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = support.newOpAsgn(support.getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[223] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = support.newOpAsgn(support.getPosition(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[224] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    ISourcePosition pos = support.getPosition(((Node)yyVals[-4+yyTop]));
                    yyVal = support.newOpConstAsgn(pos, support.new_colon2(pos, ((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-2+yyTop])), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[225] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    ISourcePosition pos = lexer.getPosition();
                    yyVal = support.newOpConstAsgn(pos, new Colon3Node(pos, ((ByteList)yyVals[-2+yyTop])), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[226] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.backrefAssignError(((Node)yyVals[-2+yyTop]));
    return yyVal;
  }
};
states[227] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[-2+yyTop]));
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
    
                    boolean isLiteral = ((Node)yyVals[-2+yyTop]) instanceof FixnumNode && ((Node)yyVals[0+yyTop]) instanceof FixnumNode;
                    yyVal = new DotNode(support.getPosition(((Node)yyVals[-2+yyTop])), support.makeNullNil(((Node)yyVals[-2+yyTop])), support.makeNullNil(((Node)yyVals[0+yyTop])), false, isLiteral);
    return yyVal;
  }
};
states[228] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[-2+yyTop]));
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));

                    boolean isLiteral = ((Node)yyVals[-2+yyTop]) instanceof FixnumNode && ((Node)yyVals[0+yyTop]) instanceof FixnumNode;
                    yyVal = new DotNode(support.getPosition(((Node)yyVals[-2+yyTop])), support.makeNullNil(((Node)yyVals[-2+yyTop])), support.makeNullNil(((Node)yyVals[0+yyTop])), true, isLiteral);
    return yyVal;
  }
};
states[229] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[230] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[231] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[232] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[233] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[234] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[235] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(support.getOperatorCallNode(((NumericNode)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition()), ((ByteList)yyVals[-3+yyTop]));
    return yyVal;
  }
};
states[236] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[0+yyTop]), ((ByteList)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[237] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[0+yyTop]), ((ByteList)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[238] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[239] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[240] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[241] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[242] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[243] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[244] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[245] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[246] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getMatchNode(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
                  /* ENEBO
                        $$ = match_op($1, $3);
                        if (nd_type($1) == NODE_LIT && TYPE($1->nd_lit) == T_REGEXP) {
                            $$ = reg_named_capture_assign($1->nd_lit, $$);
                        }
                  */
    return yyVal;
  }
};
states[247] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[248] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(support.getConditionNode(((Node)yyVals[0+yyTop])), ((ByteList)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[249] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[0+yyTop]), ((ByteList)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[250] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[251] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[252] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newAndNode(((Node)yyVals[-2+yyTop]).getPosition(), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[253] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newOrNode(((Node)yyVals[-2+yyTop]).getPosition(), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[254] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_defined(((ISourcePosition)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[255] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[-5+yyTop]));
                    yyVal = new IfNode(support.getPosition(((Node)yyVals[-5+yyTop])), support.getConditionNode(((Node)yyVals[-5+yyTop])), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[256] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[257] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[258] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[259] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[260] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[261] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[262] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     support.warning(ID.MISCELLANEOUS, lexer.getPosition(), "comparison '" + ((ByteList)yyVals[-1+yyTop]) + "' after comparison");
                     yyVal = support.getOperatorCallNode(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), lexer.getPosition());
    return yyVal;
  }
};
states[263] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = support.makeNullNil(((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[265] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[266] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.arg_append(((Node)yyVals[-3+yyTop]), support.remove_duplicate_keys(((HashNode)yyVals[-1+yyTop])));
    return yyVal;
  }
};
states[267] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(((HashNode)yyVals[-1+yyTop]).getPosition(), support.remove_duplicate_keys(((HashNode)yyVals[-1+yyTop])));
    return yyVal;
  }
};
states[268] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[269] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[-2+yyTop]));
                    yyVal = support.newRescueModNode(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[270] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
                    if (yyVal != null) ((Node)yyVal).setPosition(((ISourcePosition)yyVals[-2+yyTop]));
    return yyVal;
  }
};
states[275] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[276] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.arg_append(((Node)yyVals[-3+yyTop]), support.remove_duplicate_keys(((HashNode)yyVals[-1+yyTop])));
    return yyVal;
  }
};
states[277] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(((HashNode)yyVals[-1+yyTop]).getPosition(), support.remove_duplicate_keys(((HashNode)yyVals[-1+yyTop])));
    return yyVal;
  }
};
states[278] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = support.newArrayNode(support.getPosition(((Node)yyVals[0+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[279] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.arg_blk_pass(((Node)yyVals[-1+yyTop]), ((BlockPassNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[280] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(((HashNode)yyVals[-1+yyTop]).getPosition(), support.remove_duplicate_keys(((HashNode)yyVals[-1+yyTop])));
                    yyVal = support.arg_blk_pass((Node)yyVal, ((BlockPassNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[281] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.arg_append(((Node)yyVals[-3+yyTop]), support.remove_duplicate_keys(((HashNode)yyVals[-1+yyTop])));
                    yyVal = support.arg_blk_pass((Node)yyVal, ((BlockPassNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[282] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
    return yyVal;
  }
};
states[283] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = Long.valueOf(lexer.getCmdArgumentState().getStack());
                    lexer.getCmdArgumentState().begin();
    return yyVal;
  }
};
states[284] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getCmdArgumentState().reset(((Long)yyVals[-1+yyTop]).longValue());
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[285] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BlockPassNode(support.getPosition(((Node)yyVals[0+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[286] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((BlockPassNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[288] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    ISourcePosition pos = ((Node)yyVals[0+yyTop]) == null ? lexer.getPosition() : ((Node)yyVals[0+yyTop]).getPosition();
                    yyVal = support.newArrayNode(pos, ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[289] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newSplatNode(support.getPosition(((Node)yyVals[0+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[290] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node node = support.splat_array(((Node)yyVals[-2+yyTop]));

                    if (node != null) {
                        yyVal = support.list_append(node, ((Node)yyVals[0+yyTop]));
                    } else {
                        yyVal = support.arg_append(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
                    }
    return yyVal;
  }
};
states[291] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node node = null;

                    /* FIXME: lose syntactical elements here (and others like this)*/
                    if (((Node)yyVals[0+yyTop]) instanceof ArrayNode &&
                        (node = support.splat_array(((Node)yyVals[-3+yyTop]))) != null) {
                        yyVal = support.list_concat(node, ((Node)yyVals[0+yyTop]));
                    } else {
                        yyVal = support.arg_concat(support.getPosition(((Node)yyVals[-3+yyTop])), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]));
                    }
    return yyVal;
  }
};
states[292] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[293] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[294] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node node = support.splat_array(((Node)yyVals[-2+yyTop]));

                    if (node != null) {
                        yyVal = support.list_append(node, ((Node)yyVals[0+yyTop]));
                    } else {
                        yyVal = support.arg_append(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
                    }
    return yyVal;
  }
};
states[295] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node node = null;

                    if (((Node)yyVals[0+yyTop]) instanceof ArrayNode &&
                        (node = support.splat_array(((Node)yyVals[-3+yyTop]))) != null) {
                        yyVal = support.list_concat(node, ((Node)yyVals[0+yyTop]));
                    } else {
                        yyVal = support.arg_concat(((Node)yyVals[-3+yyTop]).getPosition(), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[0+yyTop]));
                    }
    return yyVal;
  }
};
states[296] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = support.newSplatNode(support.getPosition(((Node)yyVals[0+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[303] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ListNode)yyVals[0+yyTop]); /* FIXME: Why complaining without $$ = $1;*/
    return yyVal;
  }
};
states[304] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ListNode)yyVals[0+yyTop]); /* FIXME: Why complaining without $$ = $1;*/
    return yyVal;
  }
};
states[307] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = support.new_fcall(((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[308] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = lexer.getCmdArgumentState().getStack();
                    lexer.getCmdArgumentState().reset();
    return yyVal;
  }
};
states[309] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getCmdArgumentState().reset(((Long)yyVals[-2+yyTop]).longValue());
                    yyVal = new BeginNode(((ISourcePosition)yyVals[-3+yyTop]), support.makeNullNil(((Node)yyVals[-1+yyTop])));
    return yyVal;
  }
};
states[310] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setState(EXPR_ENDARG);
    return yyVal;
  }
};
states[311] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null; /*FIXME: Should be implicit nil?*/
    return yyVal;
  }
};
states[312] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = lexer.getCmdArgumentState().getStack();
                    lexer.getCmdArgumentState().reset();
    return yyVal;
  }
};
states[313] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setState(EXPR_ENDARG); 
    return yyVal;
  }
};
states[314] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getCmdArgumentState().reset(((Long)yyVals[-3+yyTop]).longValue());
                    yyVal = ((Node)yyVals[-2+yyTop]);
    return yyVal;
  }
};
states[315] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-1+yyTop]) != null) {
                        /* compstmt position includes both parens around it*/
                        ((ISourcePositionHolder) ((Node)yyVals[-1+yyTop])).setPosition(((ISourcePosition)yyVals[-2+yyTop]));
                        yyVal = ((Node)yyVals[-1+yyTop]);
                    } else {
                        yyVal = new NilNode(((ISourcePosition)yyVals[-2+yyTop]));
                    }
    return yyVal;
  }
};
states[316] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_colon2(support.getPosition(((Node)yyVals[-2+yyTop])), ((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[317] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_colon3(lexer.tokline, ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[318] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    ISourcePosition position = support.getPosition(((Node)yyVals[-1+yyTop]));
                    if (((Node)yyVals[-1+yyTop]) == null) {
                        yyVal = new ZArrayNode(position); /* zero length array */
                    } else {
                        yyVal = ((Node)yyVals[-1+yyTop]);
                    }
    return yyVal;
  }
};
states[319] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((HashNode)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[320] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ReturnNode(((ISourcePosition)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[321] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_yield(((ISourcePosition)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[322] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new YieldNode(((ISourcePosition)yyVals[-2+yyTop]), null);
    return yyVal;
  }
};
states[323] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new YieldNode(((ISourcePosition)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[324] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_defined(((ISourcePosition)yyVals[-4+yyTop]), ((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[325] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(support.getConditionNode(((Node)yyVals[-1+yyTop])), lexer.BANG);
    return yyVal;
  }
};
states[326] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.getOperatorCallNode(NilImplicitNode.NIL, lexer.BANG);
    return yyVal;
  }
};
states[327] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.frobnicate_fcall_args(((FCallNode)yyVals[-1+yyTop]), null, ((IterNode)yyVals[0+yyTop]));
                    yyVal = ((FCallNode)yyVals[-1+yyTop]);                    
    return yyVal;
  }
};
states[329] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-1+yyTop]) != null && 
                          ((BlockAcceptingNode)yyVals[-1+yyTop]).getIterNode() instanceof BlockPassNode) {
                          lexer.compile_error(PID.BLOCK_ARG_AND_BLOCK_GIVEN, "Both block arg and actual block given.");
                    }
                    yyVal = ((BlockAcceptingNode)yyVals[-1+yyTop]).setIterNode(((IterNode)yyVals[0+yyTop]));
                    ((Node)yyVal).setPosition(((Node)yyVals[-1+yyTop]).getPosition());
    return yyVal;
  }
};
states[330] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((LambdaNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[331] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IfNode(((ISourcePosition)yyVals[-5+yyTop]), support.getConditionNode(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[332] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IfNode(((ISourcePosition)yyVals[-5+yyTop]), support.getConditionNode(((Node)yyVals[-4+yyTop])), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[-2+yyTop]));
    return yyVal;
  }
};
states[333] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getConditionState().begin();
    return yyVal;
  }
};
states[334] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getConditionState().end();
    return yyVal;
  }
};
states[335] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = support.makeNullNil(((Node)yyVals[-1+yyTop]));
                    yyVal = new WhileNode(((ISourcePosition)yyVals[-6+yyTop]), support.getConditionNode(((Node)yyVals[-4+yyTop])), body);
    return yyVal;
  }
};
states[336] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                  lexer.getConditionState().begin();
    return yyVal;
  }
};
states[337] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                  lexer.getConditionState().end();
    return yyVal;
  }
};
states[338] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = support.makeNullNil(((Node)yyVals[-1+yyTop]));
                    yyVal = new UntilNode(((ISourcePosition)yyVals[-6+yyTop]), support.getConditionNode(((Node)yyVals[-4+yyTop])), body);
    return yyVal;
  }
};
states[339] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newCaseNode(((ISourcePosition)yyVals[-4+yyTop]), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[340] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newCaseNode(((ISourcePosition)yyVals[-3+yyTop]), null, ((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[341] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getConditionState().begin();
    return yyVal;
  }
};
states[342] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.getConditionState().end();
    return yyVal;
  }
};
states[343] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                      /* ENEBO: Lots of optz in 1.9 parser here*/
                    yyVal = new ForNode(((ISourcePosition)yyVals[-8+yyTop]), ((Node)yyVals[-7+yyTop]), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[-4+yyTop]), support.getCurrentScope());
    return yyVal;
  }
};
states[344] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef()) {
                        support.yyerror("class definition in method body");
                    }
                    support.pushLocalScope();
                    yyVal = support.isInClass(); /* MRI reuses $1 but we use the value for position.*/
                    support.setIsInClass(true);
    return yyVal;
  }
};
states[345] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = support.makeNullNil(((Node)yyVals[-1+yyTop]));

                    yyVal = new ClassNode(((ISourcePosition)yyVals[-5+yyTop]), ((Colon3Node)yyVals[-4+yyTop]), support.getCurrentScope(), body, ((Node)yyVals[-3+yyTop]), lexer.getRubySourceline());
                    support.popCurrentScope();
                    support.setIsInClass(((Boolean)yyVals[-2+yyTop]).booleanValue());
    return yyVal;
  }
};
states[346] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new Integer((support.isInClass() ? 2 : 0) & (support.isInDef() ? 1 : 0));
                    support.setInDef(false);
    return yyVal;
  }
};
states[347] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = Integer.valueOf(support.getInSingle());
                    support.setInSingle(0);
                    support.pushLocalScope();
    return yyVal;
  }
};
states[348] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = support.makeNullNil(((Node)yyVals[-1+yyTop]));

                    yyVal = new SClassNode(((ISourcePosition)yyVals[-7+yyTop]), ((Node)yyVals[-5+yyTop]), support.getCurrentScope(), body, lexer.getRubySourceline());
                    support.popCurrentScope();
                    support.setInDef(((((Integer)yyVals[-4+yyTop]).intValue()) & 1) != 0);
                    support.setIsInClass(((((Integer)yyVals[-4+yyTop]).intValue()) & 2) != 0);
                    support.setInSingle(((Integer)yyVals[-2+yyTop]).intValue());
    return yyVal;
  }
};
states[349] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef()) { 
                        support.yyerror("module definition in method body");
                    }
                    yyVal = support.isInClass();
                    support.setIsInClass(true);
                    support.pushLocalScope();
    return yyVal;
  }
};
states[350] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = support.makeNullNil(((Node)yyVals[-1+yyTop]));

                    yyVal = new ModuleNode(((ISourcePosition)yyVals[-4+yyTop]), ((Colon3Node)yyVals[-3+yyTop]), support.getCurrentScope(), body, lexer.getRubySourceline());
                    support.popCurrentScope();
                    support.setIsInClass(((Boolean)yyVals[-2+yyTop]).booleanValue());
    return yyVal;
  }
};
states[351] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.pushLocalScope();
                    yyVal = lexer.getCurrentArg();
                    lexer.setCurrentArg(null);
    return yyVal;
  }
};
states[352] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.isInDef();
                    support.setInDef(true);
    return yyVal;
  }
};
states[353] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = support.makeNullNil(((Node)yyVals[-1+yyTop]));

                    yyVal = new DefnNode(((ISourcePosition)yyVals[-6+yyTop]), ((ByteList)yyVals[-5+yyTop]), (ArgsNode) yyVals[-2+yyTop], support.getCurrentScope(), body, ((ISourcePosition)yyVals[0+yyTop]).getLine());
                    support.popCurrentScope();
                    support.setInDef(((Boolean)yyVals[-3+yyTop]).booleanValue());
                    lexer.setCurrentArg(((ByteList)yyVals[-4+yyTop]));
    return yyVal;
  }
};
states[354] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setState(EXPR_FNAME);
                    yyVal = support.isInDef();
                    support.setInDef(true);
    return yyVal;
  }
};
states[355] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.setInSingle(support.getInSingle() + 1);
                    support.pushLocalScope();
                    lexer.setState(EXPR_ENDFN|EXPR_LABEL); /* force for args */
                    yyVal = lexer.getCurrentArg();
                    lexer.setCurrentArg(null);
    return yyVal;
  }
};
states[356] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node body = ((Node)yyVals[-1+yyTop]);
                    if (body == null) body = NilImplicitNode.NIL;

                    yyVal = new DefsNode(((ISourcePosition)yyVals[-8+yyTop]), ((Node)yyVals[-7+yyTop]), ((ByteList)yyVals[-4+yyTop]), (ArgsNode) yyVals[-2+yyTop], support.getCurrentScope(), body, ((ISourcePosition)yyVals[0+yyTop]).getLine());
                    support.popCurrentScope();
                    support.setInSingle(support.getInSingle() - 1);
                    support.setInDef(((Boolean)yyVals[-5+yyTop]).booleanValue());
                    lexer.setCurrentArg(((ByteList)yyVals[-3+yyTop]));
    return yyVal;
  }
};
states[357] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BreakNode(((ISourcePosition)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[358] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new NextNode(((ISourcePosition)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[359] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new RedoNode(((ISourcePosition)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[360] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new RetryNode(((ISourcePosition)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[361] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = ((Node)yyVals[0+yyTop]);
                    if (yyVal == null) yyVal = NilImplicitNode.NIL;
    return yyVal;
  }
};
states[362] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ISourcePosition)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[363] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ISourcePosition)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[364] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInClass() && !support.isInDef() && !support.getCurrentScope().isBlockScope()) {
                        lexer.compile_error(PID.TOP_LEVEL_RETURN, "Invalid return in class/module body");
                    }
                    yyVal = ((ISourcePosition)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[371] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new IfNode(((ISourcePosition)yyVals[-4+yyTop]), support.getConditionNode(((Node)yyVals[-3+yyTop])), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[373] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[375] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
    return yyVal;
  }
};
states[376] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = support.assignableInCurr(((ByteList)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[377] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[378] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(((Node)yyVals[0+yyTop]).getPosition(), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[379] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[380] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ListNode)yyVals[0+yyTop]).getPosition(), ((ListNode)yyVals[0+yyTop]), null, null);
    return yyVal;
  }
};
states[381] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ListNode)yyVals[-3+yyTop]).getPosition(), ((ListNode)yyVals[-3+yyTop]), support.assignableInCurr(((ByteList)yyVals[0+yyTop]), null), null);
    return yyVal;
  }
};
states[382] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ListNode)yyVals[-5+yyTop]).getPosition(), ((ListNode)yyVals[-5+yyTop]), support.assignableInCurr(((ByteList)yyVals[-2+yyTop]), null), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[383] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ListNode)yyVals[-2+yyTop]).getPosition(), ((ListNode)yyVals[-2+yyTop]), new StarNode(lexer.getPosition()), null);
    return yyVal;
  }
};
states[384] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(((ListNode)yyVals[-4+yyTop]).getPosition(), ((ListNode)yyVals[-4+yyTop]), new StarNode(lexer.getPosition()), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[385] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(lexer.getPosition(), null, support.assignableInCurr(((ByteList)yyVals[0+yyTop]), null), null);
    return yyVal;
  }
};
states[386] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(lexer.getPosition(), null, support.assignableInCurr(((ByteList)yyVals[-2+yyTop]), null), ((ListNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[387] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(lexer.getPosition(), null, new StarNode(lexer.getPosition()), null);
    return yyVal;
  }
};
states[388] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new MultipleAsgnNode(support.getPosition(((ListNode)yyVals[0+yyTop])), null, null, ((ListNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[389] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args_tail(((ListNode)yyVals[-3+yyTop]).getPosition(), ((ListNode)yyVals[-3+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[390] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args_tail(((ListNode)yyVals[-1+yyTop]).getPosition(), ((ListNode)yyVals[-1+yyTop]), (ByteList) null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[391] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args_tail(lexer.getPosition(), null, ((ByteList)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[392] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args_tail(((BlockArgNode)yyVals[0+yyTop]).getPosition(), null, (ByteList) null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[393] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ArgsTailHolder)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[394] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args_tail(lexer.getPosition(), null, (ByteList) null, null);
    return yyVal;
  }
};
states[395] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-5+yyTop]).getPosition(), ((ListNode)yyVals[-5+yyTop]), ((ListNode)yyVals[-3+yyTop]), ((RestArgNode)yyVals[-1+yyTop]), null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[396] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-7+yyTop]).getPosition(), ((ListNode)yyVals[-7+yyTop]), ((ListNode)yyVals[-5+yyTop]), ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[397] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-3+yyTop]).getPosition(), ((ListNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), null, null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[398] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-5+yyTop]).getPosition(), ((ListNode)yyVals[-5+yyTop]), ((ListNode)yyVals[-3+yyTop]), null, ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[399] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-3+yyTop]).getPosition(), ((ListNode)yyVals[-3+yyTop]), null, ((RestArgNode)yyVals[-1+yyTop]), null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[400] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    RestArgNode rest = new UnnamedRestArgNode(((ListNode)yyVals[-1+yyTop]).getPosition(), null, support.getCurrentScope().addVariable("*"));
                    yyVal = support.new_args(((ListNode)yyVals[-1+yyTop]).getPosition(), ((ListNode)yyVals[-1+yyTop]), null, rest, null, (ArgsTailHolder) null);
    return yyVal;
  }
};
states[401] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-5+yyTop]).getPosition(), ((ListNode)yyVals[-5+yyTop]), null, ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[402] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-1+yyTop]).getPosition(), ((ListNode)yyVals[-1+yyTop]), null, null, null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[403] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(support.getPosition(((ListNode)yyVals[-3+yyTop])), null, ((ListNode)yyVals[-3+yyTop]), ((RestArgNode)yyVals[-1+yyTop]), null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[404] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(support.getPosition(((ListNode)yyVals[-5+yyTop])), null, ((ListNode)yyVals[-5+yyTop]), ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[405] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(support.getPosition(((ListNode)yyVals[-1+yyTop])), null, ((ListNode)yyVals[-1+yyTop]), null, null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[406] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-3+yyTop]).getPosition(), null, ((ListNode)yyVals[-3+yyTop]), null, ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[407] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((RestArgNode)yyVals[-1+yyTop]).getPosition(), null, null, ((RestArgNode)yyVals[-1+yyTop]), null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[408] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((RestArgNode)yyVals[-3+yyTop]).getPosition(), null, null, ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[409] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ArgsTailHolder)yyVals[0+yyTop]).getPosition(), null, null, null, null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[410] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
    /* was $$ = null;*/
                    yyVal = support.new_args(lexer.getPosition(), null, null, null, null, (ArgsTailHolder) null);
    return yyVal;
  }
};
states[411] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.commandStart = true;
                    yyVal = ((ArgsNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[412] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setCurrentArg(null);
                    yyVal = support.new_args(lexer.getPosition(), null, null, null, null, (ArgsTailHolder) null);
    return yyVal;
  }
};
states[413] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(lexer.getPosition(), null, null, null, null, (ArgsTailHolder) null);
    return yyVal;
  }
};
states[414] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setCurrentArg(null);
                    yyVal = ((ArgsNode)yyVals[-2+yyTop]);
    return yyVal;
  }
};
states[415] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
  }
};
states[416] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
  }
};
states[417] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
  }
};
states[418] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
  }
};
states[419] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.new_bv(((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[420] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
  }
};
states[421] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.pushBlockScope();
                    yyVal = lexer.getLeftParenBegin();
                    lexer.setLeftParenBegin(lexer.incrementParenNest());
    return yyVal;
  }
};
states[422] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new LambdaNode(((ArgsNode)yyVals[-1+yyTop]).getPosition(), ((ArgsNode)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), support.getCurrentScope());
                    support.popCurrentScope();
                    lexer.setLeftParenBegin(((Integer)yyVals[-2+yyTop]));
    return yyVal;
  }
};
states[423] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ArgsNode)yyVals[-2+yyTop]);
    return yyVal;
  }
};
states[424] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ArgsNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[425] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[426] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[427] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((IterNode)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[428] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    /* Workaround for JRUBY-2326 (MRI does not enter this production for some reason)*/
                    if (((Node)yyVals[-1+yyTop]) instanceof YieldNode) {
                        lexer.compile_error(PID.BLOCK_GIVEN_TO_YIELD, "block given to yield");
                    }
                    if (((Node)yyVals[-1+yyTop]) instanceof BlockAcceptingNode && ((BlockAcceptingNode)yyVals[-1+yyTop]).getIterNode() instanceof BlockPassNode) {
                        lexer.compile_error(PID.BLOCK_ARG_AND_BLOCK_GIVEN, "Both block arg and actual block given.");
                    }
                    if (((Node)yyVals[-1+yyTop]) instanceof NonLocalControlFlowNode) {
                        ((BlockAcceptingNode) ((NonLocalControlFlowNode)yyVals[-1+yyTop]).getValueNode()).setIterNode(((IterNode)yyVals[0+yyTop]));
                    } else {
                        ((BlockAcceptingNode)yyVals[-1+yyTop]).setIterNode(((IterNode)yyVals[0+yyTop]));
                    }
                    yyVal = ((Node)yyVals[-1+yyTop]);
                    ((Node)yyVal).setPosition(((Node)yyVals[-1+yyTop]).getPosition());
    return yyVal;
  }
};
states[429] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[430] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-3+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), ((IterNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[431] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-4+yyTop]), ((ByteList)yyVals[-3+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((Node)yyVals[-1+yyTop]), ((IterNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[432] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.frobnicate_fcall_args(((FCallNode)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
                    yyVal = ((FCallNode)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[433] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((ByteList)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[434] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-3+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[435] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[0+yyTop]), null, null);
    return yyVal;
  }
};
states[436] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-2+yyTop]), ((ByteList)yyVals[-1+yyTop]), LexingCommon.CALL, ((Node)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[437] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_call(((Node)yyVals[-2+yyTop]), LexingCommon.CALL, ((Node)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[438] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_super(((ISourcePosition)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[439] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ZSuperNode(((ISourcePosition)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[440] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-3+yyTop]) instanceof SelfNode) {
                        yyVal = support.new_fcall(LexingCommon.LBRACKET_RBRACKET);
                        support.frobnicate_fcall_args(((FCallNode)yyVal), ((Node)yyVals[-1+yyTop]), null);
                    } else {
                        yyVal = support.new_call(((Node)yyVals[-3+yyTop]), lexer.LBRACKET_RBRACKET, ((Node)yyVals[-1+yyTop]), null);
                    }
    return yyVal;
  }
};
states[441] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((IterNode)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[442] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((IterNode)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[443] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.pushBlockScope();
                    yyVal = Long.valueOf(lexer.getCmdArgumentState().getStack()) >> 1;
                    lexer.getCmdArgumentState().reset();
    return yyVal;
  }
};
states[444] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    /* FIXME: probably need to correct location here*/
                    yyVal = new IterNode(lexer.getPosition(), ((ArgsNode)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), support.getCurrentScope());
                     support.popCurrentScope();
                    lexer.getCmdArgumentState().reset(((Long)yyVals[-2+yyTop]).longValue());
    return yyVal;
  }
};
states[445] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.pushBlockScope();
                    yyVal = Long.valueOf(lexer.getCmdArgumentState().getStack());
                    lexer.getCmdArgumentState().reset();
    return yyVal;
  }
};
states[446] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    /* FIXME: probably need to correct location here*/
                    yyVal = new IterNode(lexer.getPosition(), ((ArgsNode)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]), support.getCurrentScope());
                     support.popCurrentScope();
                    lexer.getCmdArgumentState().reset(((Long)yyVals[-2+yyTop]).longValue());
    return yyVal;
  }
};
states[447] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newWhenNode(((ISourcePosition)yyVals[-4+yyTop]), ((Node)yyVals[-3+yyTop]), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[450] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node node;
                    if (((Node)yyVals[-3+yyTop]) != null) {
                        node = support.appendToBlock(support.node_assign(((Node)yyVals[-3+yyTop]), new GlobalVarNode(((ISourcePosition)yyVals[-5+yyTop]), lexer.DOLLAR_BANG)), ((Node)yyVals[-1+yyTop]));
                        if (((Node)yyVals[-1+yyTop]) != null) {
                            node.setPosition(((ISourcePosition)yyVals[-5+yyTop]));
                        }
                    } else {
                        node = ((Node)yyVals[-1+yyTop]);
                    }
                    Node body = support.makeNullNil(node);
                    yyVal = new RescueBodyNode(((ISourcePosition)yyVals[-5+yyTop]), ((Node)yyVals[-4+yyTop]), body, ((RescueBodyNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[451] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null; 
    return yyVal;
  }
};
states[452] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newArrayNode(((Node)yyVals[0+yyTop]).getPosition(), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[453] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.splat_array(((Node)yyVals[0+yyTop]));
                    if (yyVal == null) yyVal = ((Node)yyVals[0+yyTop]); /* ArgsCat or ArgsPush*/
    return yyVal;
  }
};
states[455] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[457] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[459] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((NumericNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[460] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.asSymbol(lexer.getPosition(), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[462] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]) instanceof EvStrNode ? new DStrNode(((Node)yyVals[0+yyTop]).getPosition(), lexer.getEncoding()).add(((Node)yyVals[0+yyTop])) : ((Node)yyVals[0+yyTop]);
                    /*
                    NODE *node = $1;
                    if (!node) {
                        node = NEW_STR(STR_NEW0());
                    } else {
                        node = evstr2dstr(node);
                    }
                    $$ = node;
                    */
    return yyVal;
  }
};
states[463] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((StrNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[464] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[465] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.literal_concat(((Node)yyVals[-1+yyTop]).getPosition(), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[466] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.heredoc_dedent(((Node)yyVals[-1+yyTop]));
		    lexer.setHeredocIndent(0);
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[467] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    ISourcePosition position = support.getPosition(((Node)yyVals[-1+yyTop]));

                    lexer.heredoc_dedent(((Node)yyVals[-1+yyTop]));
		    lexer.setHeredocIndent(0);

                    if (((Node)yyVals[-1+yyTop]) == null) {
                        yyVal = new XStrNode(position, null, StringSupport.CR_7BIT);
                    } else if (((Node)yyVals[-1+yyTop]) instanceof StrNode) {
                        yyVal = new XStrNode(position, (ByteList) ((StrNode)yyVals[-1+yyTop]).getValue().clone(), ((StrNode)yyVals[-1+yyTop]).getCodeRange());
                    } else if (((Node)yyVals[-1+yyTop]) instanceof DStrNode) {
                        yyVal = new DXStrNode(position, ((DStrNode)yyVals[-1+yyTop]));

                        ((Node)yyVal).setPosition(position);
                    } else {
                        yyVal = new DXStrNode(position).add(((Node)yyVals[-1+yyTop]));
                    }
    return yyVal;
  }
};
states[468] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.newRegexpNode(support.getPosition(((Node)yyVals[-1+yyTop])), ((Node)yyVals[-1+yyTop]), (RegexpNode) ((RegexpNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[469] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[470] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ArrayNode(lexer.getPosition());
    return yyVal;
  }
};
states[471] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[-1+yyTop]) instanceof EvStrNode ? new DStrNode(((ListNode)yyVals[-2+yyTop]).getPosition(), lexer.getEncoding()).add(((Node)yyVals[-1+yyTop])) : ((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[472] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[473] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = support.literal_concat(support.getPosition(((Node)yyVals[-1+yyTop])), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[474] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[475] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ArrayNode(lexer.getPosition());
    return yyVal;
  }
};
states[476] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[-1+yyTop]) instanceof EvStrNode ? new DSymbolNode(((ListNode)yyVals[-2+yyTop]).getPosition()).add(((Node)yyVals[-1+yyTop])) : support.asSymbol(((ListNode)yyVals[-2+yyTop]).getPosition(), ((Node)yyVals[-1+yyTop])));
    return yyVal;
  }
};
states[477] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[478] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[479] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ArrayNode(lexer.getPosition());
    return yyVal;
  }
};
states[480] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[481] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ArrayNode(lexer.getPosition());
    return yyVal;
  }
};
states[482] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(support.asSymbol(((ListNode)yyVals[-2+yyTop]).getPosition(), ((Node)yyVals[-1+yyTop])));
    return yyVal;
  }
};
states[483] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    ByteList aChar = ByteList.create("");
                    aChar.setEncoding(lexer.getEncoding());
                    yyVal = lexer.createStr(aChar, 0);
    return yyVal;
  }
};
states[484] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.literal_concat(((Node)yyVals[-1+yyTop]).getPosition(), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[485] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
  }
};
states[486] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.literal_concat(support.getPosition(((Node)yyVals[-1+yyTop])), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[487] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
  }
};
states[488] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
    /* FIXME: mri is different here.*/
                    yyVal = support.literal_concat(support.getPosition(((Node)yyVals[-1+yyTop])), ((Node)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[489] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[490] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = lexer.getStrTerm();
                    lexer.setStrTerm(null);
                    lexer.setState(EXPR_BEG);
    return yyVal;
  }
};
states[491] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setStrTerm(((StrTerm)yyVals[-1+yyTop]));
                    yyVal = new EvStrNode(support.getPosition(((Node)yyVals[0+yyTop])), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[492] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = lexer.getStrTerm();
                   lexer.setStrTerm(null);
                   lexer.getConditionState().stop();
    return yyVal;
  }
};
states[493] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = lexer.getCmdArgumentState().getStack();
                   lexer.getCmdArgumentState().reset();
    return yyVal;
  }
};
states[494] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = lexer.getState();
                   lexer.setState(EXPR_BEG);
    return yyVal;
  }
};
states[495] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = lexer.getBraceNest();
                   lexer.setBraceNest(0);
    return yyVal;
  }
};
states[496] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = lexer.getHeredocIndent();
                   lexer.setHeredocIndent(0);
    return yyVal;
  }
};
states[497] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   lexer.getConditionState().restart();
                   lexer.setStrTerm(((StrTerm)yyVals[-6+yyTop]));
                   lexer.getCmdArgumentState().reset(((Long)yyVals[-5+yyTop]).longValue());
                   lexer.setState(((Integer)yyVals[-4+yyTop]));
                   lexer.setBraceNest(((Integer)yyVals[-3+yyTop]));
                   lexer.setHeredocIndent(((Integer)yyVals[-2+yyTop]));
                   lexer.setHeredocLineIndent(-1);

                   yyVal = support.newEvStrNode(support.getPosition(((Node)yyVals[-1+yyTop])), ((Node)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[498] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = new GlobalVarNode(lexer.getPosition(), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[499] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = new InstVarNode(lexer.getPosition(), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[500] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = new ClassVarNode(lexer.getPosition(), ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[502] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     lexer.setState(EXPR_ENDARG);
                     yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[504] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[505] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[506] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[507] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     lexer.setState(EXPR_ENDARG);

                     /* DStrNode: :"some text #{some expression}"*/
                     /* StrNode: :"some text"*/
                     /* EvStrNode :"#{some expression}"*/
                     /* Ruby 1.9 allows empty strings as symbols*/
                     if (((Node)yyVals[-1+yyTop]) == null) {
                         yyVal = support.asSymbol(lexer.getPosition(), new ByteList(new byte[] {}));
                     } else if (((Node)yyVals[-1+yyTop]) instanceof DStrNode) {
                         yyVal = new DSymbolNode(((Node)yyVals[-1+yyTop]).getPosition(), ((DStrNode)yyVals[-1+yyTop]));
                     } else if (((Node)yyVals[-1+yyTop]) instanceof StrNode) {
                         yyVal = support.asSymbol(((Node)yyVals[-1+yyTop]).getPosition(), ((Node)yyVals[-1+yyTop]));
                     } else {
                         yyVal = new DSymbolNode(((Node)yyVals[-1+yyTop]).getPosition());
                         ((DSymbolNode)yyVal).add(((Node)yyVals[-1+yyTop]));
                     }
    return yyVal;
  }
};
states[508] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((NumericNode)yyVals[0+yyTop]);  
    return yyVal;
  }
};
states[509] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = support.negateNumeric(((NumericNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[510] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[511] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((FloatNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[512] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((RationalNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[513] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                     yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[514] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.declareIdentifier(((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[515] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new InstVarNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[516] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new GlobalVarNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[517] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ConstNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[518] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ClassVarNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[519] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new NilNode(lexer.tokline);
    return yyVal;
  }
};
states[520] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new SelfNode(lexer.tokline);
    return yyVal;
  }
};
states[521] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new TrueNode(lexer.tokline);
    return yyVal;
  }
};
states[522] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new FalseNode(lexer.tokline);
    return yyVal;
  }
};
states[523] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new FileNode(lexer.tokline, new ByteList(lexer.getFile().getBytes(),
                    support.getConfiguration().getRuntime().getEncodingService().getLocaleEncoding()));
    return yyVal;
  }
};
states[524] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new FixnumNode(lexer.tokline, lexer.tokline.getLine()+1);
    return yyVal;
  }
};
states[525] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new EncodingNode(lexer.tokline, lexer.getEncoding());
    return yyVal;
  }
};
states[526] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.assignableLabelOrIdentifier(((ByteList)yyVals[0+yyTop]), null);
    return yyVal;
  }
};
states[527] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = new InstAsgnNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[528] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = new GlobalAsgnNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[529] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (support.isInDef() || support.isInSingle()) support.compile_error("dynamic constant assignment");

                    yyVal = new ConstDeclNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), null, NilImplicitNode.NIL);
    return yyVal;
  }
};
states[530] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ClassVarAsgnNode(lexer.tokline, ((ByteList)yyVals[0+yyTop]), NilImplicitNode.NIL);
    return yyVal;
  }
};
states[531] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to nil");
                    yyVal = null;
    return yyVal;
  }
};
states[532] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't change the value of self");
                    yyVal = null;
    return yyVal;
  }
};
states[533] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to true");
                    yyVal = null;
    return yyVal;
  }
};
states[534] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to false");
                    yyVal = null;
    return yyVal;
  }
};
states[535] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to __FILE__");
                    yyVal = null;
    return yyVal;
  }
};
states[536] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to __LINE__");
                    yyVal = null;
    return yyVal;
  }
};
states[537] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.compile_error("Can't assign to __ENCODING__");
                    yyVal = null;
    return yyVal;
  }
};
states[538] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[539] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[540] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   lexer.setState(EXPR_BEG);
                   lexer.commandStart = true;
    return yyVal;
  }
};
states[541] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[542] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = null;
    return yyVal;
  }
};
states[543] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ArgsNode)yyVals[-1+yyTop]);
                    lexer.setState(EXPR_BEG);
                    lexer.commandStart = true;
    return yyVal;
  }
};
states[544] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   yyVal = lexer.inKwarg;
                   lexer.inKwarg = true;
                   lexer.setState(lexer.getState() | EXPR_LABEL);
    return yyVal;
  }
};
states[545] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                   lexer.inKwarg = ((Boolean)yyVals[-2+yyTop]);
                    yyVal = ((ArgsNode)yyVals[-1+yyTop]);
                    lexer.setState(EXPR_BEG);
                    lexer.commandStart = true;
    return yyVal;
  }
};
states[546] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args_tail(((ListNode)yyVals[-3+yyTop]).getPosition(), ((ListNode)yyVals[-3+yyTop]), ((ByteList)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[547] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args_tail(((ListNode)yyVals[-1+yyTop]).getPosition(), ((ListNode)yyVals[-1+yyTop]), (ByteList) null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[548] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args_tail(lexer.getPosition(), null, ((ByteList)yyVals[-1+yyTop]), ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[549] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args_tail(((BlockArgNode)yyVals[0+yyTop]).getPosition(), null, (ByteList) null, ((BlockArgNode)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[550] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ArgsTailHolder)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[551] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args_tail(lexer.getPosition(), null, (ByteList) null, null);
    return yyVal;
  }
};
states[552] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-5+yyTop]).getPosition(), ((ListNode)yyVals[-5+yyTop]), ((ListNode)yyVals[-3+yyTop]), ((RestArgNode)yyVals[-1+yyTop]), null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[553] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-7+yyTop]).getPosition(), ((ListNode)yyVals[-7+yyTop]), ((ListNode)yyVals[-5+yyTop]), ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[554] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-3+yyTop]).getPosition(), ((ListNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), null, null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[555] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-5+yyTop]).getPosition(), ((ListNode)yyVals[-5+yyTop]), ((ListNode)yyVals[-3+yyTop]), null, ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[556] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-3+yyTop]).getPosition(), ((ListNode)yyVals[-3+yyTop]), null, ((RestArgNode)yyVals[-1+yyTop]), null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[557] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-5+yyTop]).getPosition(), ((ListNode)yyVals[-5+yyTop]), null, ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[558] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-1+yyTop]).getPosition(), ((ListNode)yyVals[-1+yyTop]), null, null, null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[559] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-3+yyTop]).getPosition(), null, ((ListNode)yyVals[-3+yyTop]), ((RestArgNode)yyVals[-1+yyTop]), null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[560] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-5+yyTop]).getPosition(), null, ((ListNode)yyVals[-5+yyTop]), ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[561] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-1+yyTop]).getPosition(), null, ((ListNode)yyVals[-1+yyTop]), null, null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[562] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ListNode)yyVals[-3+yyTop]).getPosition(), null, ((ListNode)yyVals[-3+yyTop]), null, ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[563] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((RestArgNode)yyVals[-1+yyTop]).getPosition(), null, null, ((RestArgNode)yyVals[-1+yyTop]), null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[564] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((RestArgNode)yyVals[-3+yyTop]).getPosition(), null, null, ((RestArgNode)yyVals[-3+yyTop]), ((ListNode)yyVals[-1+yyTop]), ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[565] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(((ArgsTailHolder)yyVals[0+yyTop]).getPosition(), null, null, null, null, ((ArgsTailHolder)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[566] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.new_args(lexer.getPosition(), null, null, null, null, (ArgsTailHolder) null);
    return yyVal;
  }
};
states[567] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.yyerror("formal argument cannot be a constant");
    return yyVal;
  }
};
states[568] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.yyerror("formal argument cannot be an instance variable");
    return yyVal;
  }
};
states[569] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.yyerror("formal argument cannot be a global variable");
    return yyVal;
  }
};
states[570] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.yyerror("formal argument cannot be a class variable");
    return yyVal;
  }
};
states[571] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]); /* Not really reached*/
    return yyVal;
  }
};
states[572] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.formal_argument(((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[573] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setCurrentArg(((ByteList)yyVals[0+yyTop]));
                    yyVal = support.arg_var(((ByteList)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[574] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setCurrentArg(null);
                    yyVal = ((ArgumentNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[575] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((Node)yyVals[-1+yyTop]);
                    /*            {
            ID tid = internal_id();
            arg_var(tid);
            if (dyna_in_block()) {
                $2->nd_value = NEW_DVAR(tid);
            }
            else {
                $2->nd_value = NEW_LVAR(tid);
            }
            $$ = NEW_ARGS_AUX(tid, 1);
            $$->nd_next = $2;*/
    return yyVal;
  }
};
states[576] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ArrayNode(lexer.getPosition(), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[577] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[0+yyTop]));
                    yyVal = ((ListNode)yyVals[-2+yyTop]);
    return yyVal;
  }
};
states[578] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.arg_var(support.formal_argument(((ByteList)yyVals[0+yyTop])));
                    lexer.setCurrentArg(((ByteList)yyVals[0+yyTop]));
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[579] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setCurrentArg(null);
                    yyVal = support.keyword_arg(((Node)yyVals[0+yyTop]).getPosition(), support.assignableKeyword(((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop])));
    return yyVal;
  }
};
states[580] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setCurrentArg(null);
                    yyVal = support.keyword_arg(lexer.getPosition(), support.assignableKeyword(((ByteList)yyVals[0+yyTop]), new RequiredKeywordArgumentValueNode()));
    return yyVal;
  }
};
states[581] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.keyword_arg(support.getPosition(((Node)yyVals[0+yyTop])), support.assignableKeyword(((ByteList)yyVals[-1+yyTop]), ((Node)yyVals[0+yyTop])));
    return yyVal;
  }
};
states[582] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.keyword_arg(lexer.getPosition(), support.assignableKeyword(((ByteList)yyVals[0+yyTop]), new RequiredKeywordArgumentValueNode()));
    return yyVal;
  }
};
states[583] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ArrayNode(((Node)yyVals[0+yyTop]).getPosition(), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[584] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[585] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new ArrayNode(((Node)yyVals[0+yyTop]).getPosition(), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[586] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ListNode)yyVals[-2+yyTop]).add(((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[587] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[588] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[589] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    support.shadowing_lvar(((ByteList)yyVals[0+yyTop]));
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[590] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.INTERNAL_ID;
    return yyVal;
  }
};
states[591] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setCurrentArg(null);
                    yyVal = new OptArgNode(support.getPosition(((Node)yyVals[0+yyTop])), support.assignableLabelOrIdentifier(((ArgumentNode)yyVals[-2+yyTop]).getByteName(), ((Node)yyVals[0+yyTop])));
    return yyVal;
  }
};
states[592] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setCurrentArg(null);
                    yyVal = new OptArgNode(support.getPosition(((Node)yyVals[0+yyTop])), support.assignableLabelOrIdentifier(((ArgumentNode)yyVals[-2+yyTop]).getByteName(), ((Node)yyVals[0+yyTop])));
    return yyVal;
  }
};
states[593] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BlockNode(((Node)yyVals[0+yyTop]).getPosition()).add(((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[594] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.appendToBlock(((ListNode)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[595] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new BlockNode(((Node)yyVals[0+yyTop]).getPosition()).add(((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[596] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.appendToBlock(((ListNode)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[597] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[598] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[599] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (!support.is_local_id(((ByteList)yyVals[0+yyTop]))) {
                        support.yyerror("rest argument must be local variable");
                    }
                    
                    yyVal = new RestArgNode(support.arg_var(support.shadowing_lvar(((ByteList)yyVals[0+yyTop]))));
    return yyVal;
  }
};
states[600] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new UnnamedRestArgNode(lexer.getPosition(), "", support.getCurrentScope().addVariable("*"));
    return yyVal;
  }
};
states[601] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[602] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[603] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (!support.is_local_id(((ByteList)yyVals[0+yyTop]))) {
                        support.yyerror("block argument must be local variable");
                    }
                    
                    yyVal = new BlockArgNode(support.arg_var(support.shadowing_lvar(((ByteList)yyVals[0+yyTop]))));
    return yyVal;
  }
};
states[604] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((BlockArgNode)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[605] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = null;
    return yyVal;
  }
};
states[606] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    value_expr(lexer, ((Node)yyVals[0+yyTop]));
                    yyVal = ((Node)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[607] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    lexer.setState(EXPR_BEG);
    return yyVal;
  }
};
states[608] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-1+yyTop]) == null) {
                        support.yyerror("can't define single method for ().");
                    } else if (((Node)yyVals[-1+yyTop]) instanceof ILiteralNode) {
                        support.yyerror("can't define single method for literals.");
                    }
                    value_expr(lexer, ((Node)yyVals[-1+yyTop]));
                    yyVal = ((Node)yyVals[-1+yyTop]);
    return yyVal;
  }
};
states[609] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new HashNode(lexer.getPosition());
    return yyVal;
  }
};
states[610] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.remove_duplicate_keys(((HashNode)yyVals[-1+yyTop]));
    return yyVal;
  }
};
states[611] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = new HashNode(lexer.getPosition(), ((KeyValuePair)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[612] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((HashNode)yyVals[-2+yyTop]).add(((KeyValuePair)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[613] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.createKeyValue(((Node)yyVals[-2+yyTop]), ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[614] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    Node label = support.asSymbol(support.getPosition(((Node)yyVals[0+yyTop])), ((ByteList)yyVals[-1+yyTop]));
                    yyVal = support.createKeyValue(label, ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[615] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    if (((Node)yyVals[-2+yyTop]) instanceof StrNode) {
                        DStrNode dnode = new DStrNode(support.getPosition(((Node)yyVals[-2+yyTop])), lexer.getEncoding());
                        dnode.add(((Node)yyVals[-2+yyTop]));
                        yyVal = support.createKeyValue(new DSymbolNode(support.getPosition(((Node)yyVals[-2+yyTop])), dnode), ((Node)yyVals[0+yyTop]));
                    } else if (((Node)yyVals[-2+yyTop]) instanceof DStrNode) {
                        yyVal = support.createKeyValue(new DSymbolNode(support.getPosition(((Node)yyVals[-2+yyTop])), ((DStrNode)yyVals[-2+yyTop])), ((Node)yyVals[0+yyTop]));
                    } else {
                        support.compile_error("Uknown type for assoc in strings: " + ((Node)yyVals[-2+yyTop]));
                    }

    return yyVal;
  }
};
states[616] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = support.createKeyValue(null, ((Node)yyVals[0+yyTop]));
    return yyVal;
  }
};
states[617] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[618] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[619] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[620] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[621] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[622] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[623] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[624] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[625] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[626] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[627] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[628] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[629] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[630] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[632] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[637] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[638] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                    yyVal = ((ByteList)yyVals[0+yyTop]);
    return yyVal;
  }
};
states[646] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                      yyVal = null;
    return yyVal;
  }
};
states[647] = new ParserState() {
  @Override public Object execute(ParserSupport support, RubyLexer lexer, Object yyVal, Object[] yyVals, int yyTop) {
                  yyVal = null;
    return yyVal;
  }
};
}
					// line 2765 "RubyParser.y"

    /** The parse method use an lexer stream and parse it to an AST node 
     * structure
     */
    public RubyParserResult parse(ParserConfiguration configuration) throws IOException {
        support.reset();
        support.setConfiguration(configuration);
        support.setResult(new RubyParserResult());
        
        yyparse(lexer, configuration.isDebug() ? new YYDebug() : null);
        
        return support.getResult();
    }
}
					// line 10595 "-"
