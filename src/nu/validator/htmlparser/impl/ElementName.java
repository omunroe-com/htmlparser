/*
 * Copyright (c) 2008 Mozilla Foundation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 */

package nu.validator.htmlparser.impl;

import java.util.Arrays;

import nu.validator.htmlparser.annotation.Local;

public class ElementName
// uncomment when regenating self
// implements Comparable<ElementName>
{

    public final static ElementName NULL_ELEMENT_NAME = new ElementName(null);

    public final String name;

    public final String camelCaseName;

    public final int group;

    public final boolean special;

    public final boolean scoping;

    public final boolean fosterParenting;

    public final boolean custom;

    static ElementName elementNameByBuffer(char[] buf, int length) {
        int hash = ElementName.bufToHash(buf, length);
        int index = Arrays.binarySearch(ElementName.ELEMENT_HASHES, hash);
        if (index < 0) {
            return new ElementName(StringUtil.localNameFromBuffer(buf, length));
        } else {
            ElementName rv = ElementName.ELEMENT_NAMES[index];
            @Local String name = rv.name;
            if (name.length() != length) {
                return new ElementName(StringUtil.localNameFromBuffer(buf,
                        length));
            }
            for (int i = 0; i < length; i++) {
                if (name.charAt(i) != buf[i]) {
                    return new ElementName(StringUtil.localNameFromBuffer(buf,
                            length));
                }
            }
            return rv;
        }
    }

    static ElementName elementNameByLocalName(@Local String name) {
        return ElementName.elementNameByBuffer(name.toCharArray(),
                name.length());
    }

    /**
     * This method has to return a unique integer for each well-known
     * lower-cased element name.
     * 
     * @param buf
     * @param len
     * @return
     */
    private static int bufToHash(char[] buf, int len) {
        int hash = len;
        hash <<= 5;
        hash += buf[0] - 0x60;
        int j = len;
        for (int i = 0; i < 4 && j > 0; i++) {
            j--;
            hash <<= 5;
            hash += buf[j] - 0x60;
        }
        return hash;
    }

    private ElementName(String name, String camelCaseName, int group,
            boolean special, boolean scoping, boolean fosterParenting) {
        this.name = name;
        this.camelCaseName = camelCaseName;
        this.group = group;
        this.special = special;
        this.scoping = scoping;
        this.fosterParenting = fosterParenting;
        this.custom = false;
    }

    private ElementName(String name) {
        this.name = name;
        this.camelCaseName = name;
        this.group = TreeBuilder.OTHER;
        this.special = false;
        this.scoping = false;
        this.fosterParenting = false;
        this.custom = true;
    }

    // START CODE ONLY USED FOR GENERATING CODE uncomment and run to regenerate

    //    
    // /**
    // * @see java.lang.Object#toString()
    // */
    // @Override public String toString() {
    // return "(\"" + name + "\", \"" + camelCaseName + "\", TreeBuilder."
    // + treeBuilderGroupToName() + ", "
    // + (special ? "true" : "false") + ", "
    // + (scoping ? "true" : "false") + ", "
    // + (fosterParenting ? "true" : "false") + ")";
    // }
    //
    // private String constName() {
    // char[] buf = new char[name.length()];
    // for (int i = 0; i < name.length(); i++) {
    // char c = name.charAt(i);
    // if (c == '-') {
    // buf[i] = '_';
    // } else if (c >= '0' && c <= '9') {
    // buf[i] = c;
    // } else {
    // buf[i] = (char) (c - 0x20);
    // }
    // }
    // return new String(buf);
    // }
    //
    // private int hash() {
    // return bufToHash(name.toCharArray(), name.length());
    // }
    //
    // public int compareTo(ElementName other) {
    // int thisHash = this.hash();
    // int otherHash = other.hash();
    // if (thisHash < otherHash) {
    // return -1;
    // } else if (thisHash == otherHash) {
    // return 0;
    // } else {
    // return 1;
    // }
    // }
    //
    // private String treeBuilderGroupToName() {
    // switch (group) {
    // case TreeBuilder.OTHER:
    // return "OTHER";
    // case TreeBuilder.A:
    // return "A";
    // case TreeBuilder.BASE:
    // return "BASE";
    // case TreeBuilder.BODY:
    // return "BODY";
    // case TreeBuilder.BR:
    // return "BR";
    // case TreeBuilder.BUTTON:
    // return "BUTTON";
    // case TreeBuilder.CAPTION:
    // return "CAPTION";
    // case TreeBuilder.COL:
    // return "COL";
    // case TreeBuilder.COLGROUP:
    // return "COLGROUP";
    // case TreeBuilder.FORM:
    // return "FORM";
    // case TreeBuilder.FRAME:
    // return "FRAME";
    // case TreeBuilder.FRAMESET:
    // return "FRAMESET";
    // case TreeBuilder.IMAGE:
    // return "IMAGE";
    // case TreeBuilder.INPUT:
    // return "INPUT";
    // case TreeBuilder.ISINDEX:
    // return "ISINDEX";
    // case TreeBuilder.LI:
    // return "LI";
    // case TreeBuilder.LINK:
    // return "LINK";
    // case TreeBuilder.MATH:
    // return "MATH";
    // case TreeBuilder.META:
    // return "META";
    // case TreeBuilder.SVG:
    // return "SVG";
    // case TreeBuilder.HEAD:
    // return "HEAD";
    // case TreeBuilder.HR:
    // return "HR";
    // case TreeBuilder.HTML:
    // return "HTML";
    // case TreeBuilder.NOBR:
    // return "NOBR";
    // case TreeBuilder.NOFRAMES:
    // return "NOFRAMES";
    // case TreeBuilder.NOSCRIPT:
    // return "NOSCRIPT";
    // case TreeBuilder.OPTGROUP:
    // return "OPTGROUP";
    // case TreeBuilder.OPTION:
    // return "OPTION";
    // case TreeBuilder.P:
    // return "P";
    // case TreeBuilder.PLAINTEXT:
    // return "PLAINTEXT";
    // case TreeBuilder.SCRIPT:
    // return "SCRIPT";
    // case TreeBuilder.SELECT:
    // return "SELECT";
    // case TreeBuilder.STYLE:
    // return "STYLE";
    // case TreeBuilder.TABLE:
    // return "TABLE";
    // case TreeBuilder.TEXTAREA:
    // return "TEXTAREA";
    // case TreeBuilder.TITLE:
    // return "TITLE";
    // case TreeBuilder.TR:
    // return "TR";
    // case TreeBuilder.XMP:
    // return "XMP";
    // case TreeBuilder.TBODY_OR_THEAD_OR_TFOOT:
    // return "TBODY_OR_THEAD_OR_TFOOT";
    // case TreeBuilder.TD_OR_TH:
    // return "TD_OR_TH";
    // case TreeBuilder.DD_OR_DT:
    // return "DD_OR_DT";
    // case TreeBuilder.H1_OR_H2_OR_H3_OR_H4_OR_H5_OR_H6:
    // return "H1_OR_H2_OR_H3_OR_H4_OR_H5_OR_H6";
    // case TreeBuilder.OBJECT_OR_MARQUEE_OR_APPLET:
    // return "OBJECT_OR_MARQUEE_OR_APPLET";
    // case TreeBuilder.PRE_OR_LISTING:
    // return "PRE_OR_LISTING";
    // case
    // TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U:
    // return
    // "B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U";
    // case TreeBuilder.UL_OR_OL_OR_DL:
    // return "UL_OR_OL_OR_DL";
    // case TreeBuilder.IFRAME_OR_NOEMBED:
    // return "IFRAME_OR_NOEMBED";
    // case TreeBuilder.EMBED_OR_IMG:
    // return "EMBED_OR_IMG";
    // case TreeBuilder.AREA_OR_BASEFONT_OR_BGSOUND_OR_PARAM_OR_SPACER_OR_WBR:
    // return "AREA_OR_BASEFONT_OR_BGSOUND_OR_PARAM_OR_SPACER_OR_WBR";
    // case TreeBuilder.DIV_OR_BLOCKQUOTE_OR_CENTER_OR_MENU:
    // return "DIV_OR_BLOCKQUOTE_OR_CENTER_OR_MENU";
    // case TreeBuilder.FIELDSET_OR_ADDRESS_OR_DIR:
    // return "FIELDSET_OR_ADDRESS_OR_DIR";
    // case TreeBuilder.CODE_OR_RUBY_OR_SPAN_OR_SUB_OR_SUP_OR_VAR:
    // return "CODE_OR_RUBY_OR_SPAN_OR_SUB_OR_SUP_OR_VAR";
    // }
    // return null;
    // }
    //
    // /**
    // * Regenerate self
    // *
    // * @param args
    // */
    // public static void main(String[] args) {
    // Arrays.sort(ELEMENT_NAMES);
    // for (int i = 1; i < ELEMENT_NAMES.length; i++) {
    // if (ELEMENT_NAMES[i].hash() == ELEMENT_NAMES[i - 1].hash()) {
    // System.err.println("Hash collision: " + ELEMENT_NAMES[i].name
    // + ", " + ELEMENT_NAMES[i - 1].name);
    // return;
    // }
    // }
    // for (int i = 0; i < ELEMENT_NAMES.length; i++) {
    // ElementName el = ELEMENT_NAMES[i];
    // System.out.println("public static final ElementName "
    // + el.constName() + " = new ElementName" + el.toString()
    // + ";");
    // }
    // System.out.println("private final static ElementName[] ELEMENT_NAMES =
    // {");
    // for (int i = 0; i < ELEMENT_NAMES.length; i++) {
    // ElementName el = ELEMENT_NAMES[i];
    // System.out.println(el.constName() + ",");
    // }
    // System.out.println("};");
    // System.out.println("private final static int[] ELEMENT_HASHES = {");
    // for (int i = 0; i < ELEMENT_NAMES.length; i++) {
    // ElementName el = ELEMENT_NAMES[i];
    // System.out.println(Integer.toString(el.hash()) + ",");
    // }
    // System.out.println("};");
    // }

    // START GENERATED CODE
    public static final ElementName A = new ElementName("a", "a",
            TreeBuilder.A, false, false, false);

    public static final ElementName B = new ElementName(
            "b",
            "b",
            TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U,
            false, false, false);

    public static final ElementName G = new ElementName("g", "g",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName I = new ElementName(
            "i",
            "i",
            TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U,
            false, false, false);

    public static final ElementName P = new ElementName("p", "p",
            TreeBuilder.P, true, false, false);

    public static final ElementName Q = new ElementName("q", "q",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName S = new ElementName(
            "s",
            "s",
            TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U,
            false, false, false);

    public static final ElementName U = new ElementName(
            "u",
            "u",
            TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U,
            false, false, false);

    public static final ElementName BR = new ElementName("br", "br",
            TreeBuilder.BR, true, false, false);

    public static final ElementName CI = new ElementName("ci", "ci",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName CN = new ElementName("cn", "cn",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName DD = new ElementName("dd", "dd",
            TreeBuilder.DD_OR_DT, true, false, false);

    public static final ElementName DL = new ElementName("dl", "dl",
            TreeBuilder.UL_OR_OL_OR_DL, true, false, false);

    public static final ElementName DT = new ElementName("dt", "dt",
            TreeBuilder.DD_OR_DT, true, false, false);

    public static final ElementName EM = new ElementName(
            "em",
            "em",
            TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U,
            false, false, false);

    public static final ElementName EQ = new ElementName("eq", "eq",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName FN = new ElementName("fn", "fn",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName H1 = new ElementName("h1", "h1",
            TreeBuilder.H1_OR_H2_OR_H3_OR_H4_OR_H5_OR_H6, true, false, false);

    public static final ElementName H2 = new ElementName("h2", "h2",
            TreeBuilder.H1_OR_H2_OR_H3_OR_H4_OR_H5_OR_H6, true, false, false);

    public static final ElementName H3 = new ElementName("h3", "h3",
            TreeBuilder.H1_OR_H2_OR_H3_OR_H4_OR_H5_OR_H6, true, false, false);

    public static final ElementName H4 = new ElementName("h4", "h4",
            TreeBuilder.H1_OR_H2_OR_H3_OR_H4_OR_H5_OR_H6, true, false, false);

    public static final ElementName H5 = new ElementName("h5", "h5",
            TreeBuilder.H1_OR_H2_OR_H3_OR_H4_OR_H5_OR_H6, true, false, false);

    public static final ElementName H6 = new ElementName("h6", "h6",
            TreeBuilder.H1_OR_H2_OR_H3_OR_H4_OR_H5_OR_H6, true, false, false);

    public static final ElementName GT = new ElementName("gt", "gt",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName HR = new ElementName("hr", "hr",
            TreeBuilder.HR, true, false, false);

    public static final ElementName IN = new ElementName("in", "in",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName LI = new ElementName("li", "li",
            TreeBuilder.LI, true, false, false);

    public static final ElementName LN = new ElementName("ln", "ln",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName LT = new ElementName("lt", "lt",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MI = new ElementName("mi", "mi",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MN = new ElementName("mn", "mn",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MO = new ElementName("mo", "mo",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MS = new ElementName("ms", "ms",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName OL = new ElementName("ol", "ol",
            TreeBuilder.UL_OR_OL_OR_DL, true, false, false);

    public static final ElementName OR = new ElementName("or", "or",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName PI = new ElementName("pi", "pi",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName TD = new ElementName("td", "td",
            TreeBuilder.TD_OR_TH, false, true, false);

    public static final ElementName TH = new ElementName("th", "th",
            TreeBuilder.TD_OR_TH, false, true, false);

    public static final ElementName TR = new ElementName("tr", "tr",
            TreeBuilder.TR, true, false, true);

    public static final ElementName TT = new ElementName(
            "tt",
            "tt",
            TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U,
            false, false, false);

    public static final ElementName UL = new ElementName("ul", "ul",
            TreeBuilder.UL_OR_OL_OR_DL, true, false, false);

    public static final ElementName AND = new ElementName("and", "and",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARG = new ElementName("arg", "arg",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName ABS = new ElementName("abs", "abs",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName BIG = new ElementName(
            "big",
            "big",
            TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U,
            false, false, false);

    public static final ElementName BDO = new ElementName("bdo", "bdo",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName CSC = new ElementName("csc", "csc",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName COL = new ElementName("col", "col",
            TreeBuilder.COL, true, false, false);

    public static final ElementName COS = new ElementName("cos", "cos",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName COT = new ElementName("cot", "cot",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName DEL = new ElementName("del", "del",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName DFN = new ElementName("dfn", "dfn",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName DIR = new ElementName("dir", "dir",
            TreeBuilder.FIELDSET_OR_ADDRESS_OR_DIR, true, false, false);

    public static final ElementName DIV = new ElementName("div", "div",
            TreeBuilder.DIV_OR_BLOCKQUOTE_OR_CENTER_OR_MENU, true, false, false);

    public static final ElementName EXP = new ElementName("exp", "exp",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName GCD = new ElementName("gcd", "gcd",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName GEQ = new ElementName("geq", "geq",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName IMG = new ElementName("img", "img",
            TreeBuilder.EMBED_OR_IMG, true, false, false);

    public static final ElementName INS = new ElementName("ins", "ins",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName INT = new ElementName("int", "int",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName KBD = new ElementName("kbd", "kbd",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName LOG = new ElementName("log", "log",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName LCM = new ElementName("lcm", "lcm",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName LEQ = new ElementName("leq", "leq",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MTD = new ElementName("mtd", "mtd",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MIN = new ElementName("min", "min",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MAP = new ElementName("map", "map",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MTR = new ElementName("mtr", "mtr",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MAX = new ElementName("max", "max",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName NEQ = new ElementName("neq", "neq",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName NOT = new ElementName("not", "not",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName NAV = new ElementName("nav", "nav",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName PRE = new ElementName("pre", "pre",
            TreeBuilder.PRE_OR_LISTING, true, false, false);

    public static final ElementName REM = new ElementName("rem", "rem",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName SUB = new ElementName("sub", "sub",
            TreeBuilder.CODE_OR_RUBY_OR_SPAN_OR_SUB_OR_SUP_OR_VAR, false,
            false, false);

    public static final ElementName SEC = new ElementName("sec", "sec",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName SVG = new ElementName("svg", "svg",
            TreeBuilder.SVG, false, false, false);

    public static final ElementName SUM = new ElementName("sum", "sum",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName SIN = new ElementName("sin", "sin",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName SEP = new ElementName("sep", "sep",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName SUP = new ElementName("sup", "sup",
            TreeBuilder.CODE_OR_RUBY_OR_SPAN_OR_SUB_OR_SUP_OR_VAR, false,
            false, false);

    public static final ElementName SET = new ElementName("set", "set",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName TAN = new ElementName("tan", "tan",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName USE = new ElementName("use", "use",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName VAR = new ElementName("var", "var",
            TreeBuilder.CODE_OR_RUBY_OR_SPAN_OR_SUB_OR_SUP_OR_VAR, false,
            false, false);

    public static final ElementName WBR = new ElementName("wbr", "wbr",
            TreeBuilder.AREA_OR_BASEFONT_OR_BGSOUND_OR_PARAM_OR_SPACER_OR_WBR,
            true, false, false);

    public static final ElementName XMP = new ElementName("xmp", "xmp",
            TreeBuilder.XMP, false, false, false);

    public static final ElementName XOR = new ElementName("xor", "xor",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName AREA = new ElementName("area", "area",
            TreeBuilder.AREA_OR_BASEFONT_OR_BGSOUND_OR_PARAM_OR_SPACER_OR_WBR,
            true, false, false);

    public static final ElementName ABBR = new ElementName("abbr", "abbr",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName BASE = new ElementName("base", "base",
            TreeBuilder.BASE, true, false, false);

    public static final ElementName BVAR = new ElementName("bvar", "bvar",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName BODY = new ElementName("body", "body",
            TreeBuilder.BODY, true, false, false);

    public static final ElementName CARD = new ElementName("card", "card",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName CODE = new ElementName("code", "code",
            TreeBuilder.CODE_OR_RUBY_OR_SPAN_OR_SUB_OR_SUP_OR_VAR, false,
            false, false);

    public static final ElementName CITE = new ElementName("cite", "cite",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName CSCH = new ElementName("csch", "csch",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName COSH = new ElementName("cosh", "cosh",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName COTH = new ElementName("coth", "coth",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName CURL = new ElementName("curl", "curl",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName DESC = new ElementName("desc", "desc",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName DIFF = new ElementName("diff", "diff",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName DEFS = new ElementName("defs", "defs",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName FORM = new ElementName("form", "form",
            TreeBuilder.FORM, true, false, false);

    public static final ElementName FONT = new ElementName(
            "font",
            "font",
            TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U,
            false, false, false);

    public static final ElementName GRAD = new ElementName("grad", "grad",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName HEAD = new ElementName("head", "head",
            TreeBuilder.HEAD, true, false, false);

    public static final ElementName HTML = new ElementName("html", "html",
            TreeBuilder.HTML, false, true, false);

    public static final ElementName LINE = new ElementName("line", "line",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName LINK = new ElementName("link", "link",
            TreeBuilder.LINK, true, false, false);

    public static final ElementName LIST = new ElementName("list", "list",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName META = new ElementName("meta", "meta",
            TreeBuilder.META, true, false, false);

    public static final ElementName MSUB = new ElementName("msub", "msub",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MODE = new ElementName("mode", "mode",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MATH = new ElementName("math", "math",
            TreeBuilder.MATH, false, false, false);

    public static final ElementName MARK = new ElementName("mark", "mark",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MASK = new ElementName("mask", "mask",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MEAN = new ElementName("mean", "mean",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MSUP = new ElementName("msup", "msup",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MENU = new ElementName("menu", "menu",
            TreeBuilder.DIV_OR_BLOCKQUOTE_OR_CENTER_OR_MENU, true, false, false);

    public static final ElementName MROW = new ElementName("mrow", "mrow",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName NONE = new ElementName("none", "none",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName NOBR = new ElementName("nobr", "nobr",
            TreeBuilder.NOBR, false, false, false);

    public static final ElementName NEST = new ElementName("nest", "nest",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName PATH = new ElementName("path", "path",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName PLUS = new ElementName("plus", "plus",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName RULE = new ElementName("rule", "rule",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName REAL = new ElementName("real", "real",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName RELN = new ElementName("reln", "reln",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName RECT = new ElementName("rect", "rect",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName ROOT = new ElementName("root", "root",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName RUBY = new ElementName("ruby", "ruby",
            TreeBuilder.CODE_OR_RUBY_OR_SPAN_OR_SUB_OR_SUP_OR_VAR, false,
            false, false);

    public static final ElementName SECH = new ElementName("sech", "sech",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName SINH = new ElementName("sinh", "sinh",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName SPAN = new ElementName("span", "span",
            TreeBuilder.CODE_OR_RUBY_OR_SPAN_OR_SUB_OR_SUP_OR_VAR, false,
            false, false);

    public static final ElementName SAMP = new ElementName("samp", "samp",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName STOP = new ElementName("stop", "stop",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName SDEV = new ElementName("sdev", "sdev",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName TIME = new ElementName("time", "time",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName TRUE = new ElementName("true", "true",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName TREF = new ElementName("tref", "tref",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName TANH = new ElementName("tanh", "tanh",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName TEXT = new ElementName("text", "text",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName VIEW = new ElementName("view", "view",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName ASIDE = new ElementName("aside", "aside",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName AUDIO = new ElementName("audio", "audio",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName APPLY = new ElementName("apply", "apply",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName EMBED = new ElementName("embed", "embed",
            TreeBuilder.EMBED_OR_IMG, true, false, false);

    public static final ElementName FRAME = new ElementName("frame", "frame",
            TreeBuilder.FRAME, true, false, false);

    public static final ElementName FALSE = new ElementName("false", "false",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName FLOOR = new ElementName("floor", "floor",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName GLYPH = new ElementName("glyph", "glyph",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName HKERN = new ElementName("hkern", "hkern",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName IMAGE = new ElementName("image", "image",
            TreeBuilder.IMAGE, true, false, false);

    public static final ElementName IDENT = new ElementName("ident", "ident",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName INPUT = new ElementName("input", "input",
            TreeBuilder.INPUT, true, false, false);

    public static final ElementName LABEL = new ElementName("label", "label",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName LIMIT = new ElementName("limit", "limit",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MFRAC = new ElementName("mfrac", "mfrac",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MPATH = new ElementName("mpath", "mpath",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName METER = new ElementName("meter", "meter",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MOVER = new ElementName("mover", "mover",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MINUS = new ElementName("minus", "minus",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MROOT = new ElementName("mroot", "mroot",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MSQRT = new ElementName("msqrt", "msqrt",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName MTEXT = new ElementName("mtext", "mtext",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName NOTIN = new ElementName("notin", "notin",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName PIECE = new ElementName("piece", "piece",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName PARAM = new ElementName("param", "param",
            TreeBuilder.AREA_OR_BASEFONT_OR_BGSOUND_OR_PARAM_OR_SPACER_OR_WBR,
            true, false, false);

    public static final ElementName POWER = new ElementName("power", "power",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName REALS = new ElementName("reals", "reals",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName STYLE = new ElementName("style", "style",
            TreeBuilder.STYLE, true, false, false);

    public static final ElementName SMALL = new ElementName(
            "small",
            "small",
            TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U,
            false, false, false);

    public static final ElementName THEAD = new ElementName("thead", "thead",
            TreeBuilder.TBODY_OR_THEAD_OR_TFOOT, true, false, true);

    public static final ElementName TABLE = new ElementName("table", "table",
            TreeBuilder.TABLE, false, true, true);

    public static final ElementName TITLE = new ElementName("title", "title",
            TreeBuilder.TITLE, true, false, false);

    public static final ElementName TSPAN = new ElementName("tspan", "tspan",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName TIMES = new ElementName("times", "times",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName TFOOT = new ElementName("tfoot", "tfoot",
            TreeBuilder.TBODY_OR_THEAD_OR_TFOOT, true, false, true);

    public static final ElementName TBODY = new ElementName("tbody", "tbody",
            TreeBuilder.TBODY_OR_THEAD_OR_TFOOT, true, false, true);

    public static final ElementName UNION = new ElementName("union", "union",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName VKERN = new ElementName("vkern", "vkern",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName VIDEO = new ElementName("video", "video",
            TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARCSEC = new ElementName("arcsec",
            "arcsec", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARCCSC = new ElementName("arccsc",
            "arccsc", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARCTAN = new ElementName("arctan",
            "arctan", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARCSIN = new ElementName("arcsin",
            "arcsin", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARCCOS = new ElementName("arccos",
            "arccos", TreeBuilder.OTHER, false, false, false);

    public static final ElementName APPLET = new ElementName("applet",
            "applet", TreeBuilder.OBJECT_OR_MARQUEE_OR_APPLET, false, true,
            false);

    public static final ElementName ARCCOT = new ElementName("arccot",
            "arccot", TreeBuilder.OTHER, false, false, false);

    public static final ElementName APPROX = new ElementName("approx",
            "approx", TreeBuilder.OTHER, false, false, false);

    public static final ElementName BUTTON = new ElementName("button",
            "button", TreeBuilder.BUTTON, false, true, false);

    public static final ElementName CIRCLE = new ElementName("circle",
            "circle", TreeBuilder.OTHER, false, false, false);

    public static final ElementName CENTER = new ElementName("center",
            "center", TreeBuilder.DIV_OR_BLOCKQUOTE_OR_CENTER_OR_MENU, true,
            false, false);

    public static final ElementName CURSOR = new ElementName("cursor",
            "cursor", TreeBuilder.OTHER, false, false, false);

    public static final ElementName CANVAS = new ElementName("canvas",
            "canvas", TreeBuilder.OTHER, false, false, false);

    public static final ElementName DIVIDE = new ElementName("divide",
            "divide", TreeBuilder.OTHER, false, false, false);

    public static final ElementName DEGREE = new ElementName("degree",
            "degree", TreeBuilder.OTHER, false, false, false);

    public static final ElementName DIALOG = new ElementName("dialog",
            "dialog", TreeBuilder.OTHER, false, false, false);

    public static final ElementName DOMAIN = new ElementName("domain",
            "domain", TreeBuilder.OTHER, false, false, false);

    public static final ElementName EXISTS = new ElementName("exists",
            "exists", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FETILE = new ElementName("fetile",
            "feTile", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FIGURE = new ElementName("figure",
            "figure", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FORALL = new ElementName("forall",
            "forall", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FILTER = new ElementName("filter",
            "filter", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FOOTER = new ElementName("footer",
            "footer", TreeBuilder.OTHER, false, false, false);

    public static final ElementName HEADER = new ElementName("header",
            "header", TreeBuilder.OTHER, false, false, false);

    public static final ElementName IFRAME = new ElementName("iframe",
            "iframe", TreeBuilder.IFRAME_OR_NOEMBED, true, false, false);

    public static final ElementName KEYGEN = new ElementName("keygen",
            "keygen", TreeBuilder.OTHER, false, false, false);

    public static final ElementName LAMBDA = new ElementName("lambda",
            "lambda", TreeBuilder.OTHER, false, false, false);

    public static final ElementName LEGEND = new ElementName("legend",
            "legend", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MSPACE = new ElementName("mspace",
            "mspace", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MTABLE = new ElementName("mtable",
            "mtable", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MSTYLE = new ElementName("mstyle",
            "mstyle", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MGLYPH = new ElementName("mglyph",
            "mglyph", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MEDIAN = new ElementName("median",
            "median", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MUNDER = new ElementName("munder",
            "munder", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MARKER = new ElementName("marker",
            "marker", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MERROR = new ElementName("merror",
            "merror", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MOMENT = new ElementName("moment",
            "moment", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MATRIX = new ElementName("matrix",
            "matrix", TreeBuilder.OTHER, false, false, false);

    public static final ElementName OPTION = new ElementName("option",
            "option", TreeBuilder.OPTION, true, false, false);

    public static final ElementName OBJECT = new ElementName("object",
            "object", TreeBuilder.OBJECT_OR_MARQUEE_OR_APPLET, false, true,
            false);

    public static final ElementName OUTPUT = new ElementName("output",
            "output", TreeBuilder.OTHER, false, false, false);

    public static final ElementName PRIMES = new ElementName("primes",
            "primes", TreeBuilder.OTHER, false, false, false);

    public static final ElementName SOURCE = new ElementName("source",
            "source", TreeBuilder.OTHER, false, false, false);

    public static final ElementName STRIKE = new ElementName(
            "strike",
            "strike",
            TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U,
            false, false, false);

    public static final ElementName STRONG = new ElementName(
            "strong",
            "strong",
            TreeBuilder.B_OR_BIG_OR_EM_OR_FONT_OR_I_OR_S_OR_SMALL_OR_STRIKE_OR_STRONG_OR_TT_OR_U,
            false, false, false);

    public static final ElementName SWITCH = new ElementName("switch",
            "switch", TreeBuilder.OTHER, false, false, false);

    public static final ElementName SYMBOL = new ElementName("symbol",
            "symbol", TreeBuilder.OTHER, false, false, false);

    public static final ElementName SPACER = new ElementName("spacer",
            "spacer",
            TreeBuilder.AREA_OR_BASEFONT_OR_BGSOUND_OR_PARAM_OR_SPACER_OR_WBR,
            true, false, false);

    public static final ElementName SELECT = new ElementName("select",
            "select", TreeBuilder.SELECT, true, false, false);

    public static final ElementName SUBSET = new ElementName("subset",
            "subset", TreeBuilder.OTHER, false, false, false);

    public static final ElementName SCRIPT = new ElementName("script",
            "script", TreeBuilder.SCRIPT, true, false, false);

    public static final ElementName TBREAK = new ElementName("tbreak",
            "tbreak", TreeBuilder.OTHER, false, false, false);

    public static final ElementName VECTOR = new ElementName("vector",
            "vector", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARTICLE = new ElementName("article",
            "article", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ANIMATE = new ElementName("animate",
            "animate", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARCSECH = new ElementName("arcsech",
            "arcsech", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARCCSCH = new ElementName("arccsch",
            "arccsch", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARCTANH = new ElementName("arctanh",
            "arctanh", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARCSINH = new ElementName("arcsinh",
            "arcsinh", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARCCOSH = new ElementName("arccosh",
            "arccosh", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ARCCOTH = new ElementName("arccoth",
            "arccoth", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ACRONYM = new ElementName("acronym",
            "acronym", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ADDRESS = new ElementName("address",
            "address", TreeBuilder.FIELDSET_OR_ADDRESS_OR_DIR, true, false,
            false);

    public static final ElementName BGSOUND = new ElementName("bgsound",
            "bgsound",
            TreeBuilder.AREA_OR_BASEFONT_OR_BGSOUND_OR_PARAM_OR_SPACER_OR_WBR,
            true, false, false);

    public static final ElementName COMMAND = new ElementName("command",
            "command", TreeBuilder.OTHER, false, false, false);

    public static final ElementName COMPOSE = new ElementName("compose",
            "compose", TreeBuilder.OTHER, false, false, false);

    public static final ElementName CEILING = new ElementName("ceiling",
            "ceiling", TreeBuilder.OTHER, false, false, false);

    public static final ElementName CSYMBOL = new ElementName("csymbol",
            "csymbol", TreeBuilder.OTHER, false, false, false);

    public static final ElementName CAPTION = new ElementName("caption",
            "caption", TreeBuilder.CAPTION, false, true, false);

    public static final ElementName DISCARD = new ElementName("discard",
            "discard", TreeBuilder.OTHER, false, false, false);

    public static final ElementName DECLARE = new ElementName("declare",
            "declare", TreeBuilder.OTHER, false, false, false);

    public static final ElementName DETAILS = new ElementName("details",
            "details", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ELLIPSE = new ElementName("ellipse",
            "ellipse", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FEFUNCA = new ElementName("fefunca",
            "feFuncA", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FEFUNCB = new ElementName("fefuncb",
            "feFuncB", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FEBLEND = new ElementName("feblend",
            "feBlend", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FEFLOOD = new ElementName("feflood",
            "feFlood", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FEIMAGE = new ElementName("feimage",
            "feImage", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FEMERGE = new ElementName("femerge",
            "feMerge", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FEFUNCG = new ElementName("fefuncg",
            "feFuncG", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FEFUNCR = new ElementName("fefuncr",
            "feFuncR", TreeBuilder.OTHER, false, false, false);

    public static final ElementName HANDLER = new ElementName("handler",
            "handler", TreeBuilder.OTHER, false, false, false);

    public static final ElementName INVERSE = new ElementName("inverse",
            "inverse", TreeBuilder.OTHER, false, false, false);

    public static final ElementName IMPLIES = new ElementName("implies",
            "implies", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ISINDEX = new ElementName("isindex",
            "isindex", TreeBuilder.ISINDEX, true, false, false);

    public static final ElementName LOGBASE = new ElementName("logbase",
            "logbase", TreeBuilder.OTHER, false, false, false);

    public static final ElementName LISTING = new ElementName("listing",
            "listing", TreeBuilder.PRE_OR_LISTING, true, false, false);

    public static final ElementName MFENCED = new ElementName("mfenced",
            "mfenced", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MPADDED = new ElementName("mpadded",
            "mpadded", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MARQUEE = new ElementName("marquee",
            "marquee", TreeBuilder.OBJECT_OR_MARQUEE_OR_APPLET, false, true,
            false);

    public static final ElementName MACTION = new ElementName("maction",
            "maction", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MSUBSUP = new ElementName("msubsup",
            "msubsup", TreeBuilder.OTHER, false, false, false);

    public static final ElementName NOEMBED = new ElementName("noembed",
            "noembed", TreeBuilder.IFRAME_OR_NOEMBED, true, false, false);

    public static final ElementName POLYGON = new ElementName("polygon",
            "polygon", TreeBuilder.OTHER, false, false, false);

    public static final ElementName PATTERN = new ElementName("pattern",
            "pattern", TreeBuilder.OTHER, false, false, false);

    public static final ElementName PRODUCT = new ElementName("product",
            "product", TreeBuilder.OTHER, false, false, false);

    public static final ElementName SETDIFF = new ElementName("setdiff",
            "setdiff", TreeBuilder.OTHER, false, false, false);

    public static final ElementName SECTION = new ElementName("section",
            "section", TreeBuilder.OTHER, false, false, false);

    public static final ElementName TENDSTO = new ElementName("tendsto",
            "tendsto", TreeBuilder.OTHER, false, false, false);

    public static final ElementName UPLIMIT = new ElementName("uplimit",
            "uplimit", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ALTGLYPH = new ElementName("altglyph",
            "altGlyph", TreeBuilder.OTHER, false, false, false);

    public static final ElementName BASEFONT = new ElementName("basefont",
            "basefont",
            TreeBuilder.AREA_OR_BASEFONT_OR_BGSOUND_OR_PARAM_OR_SPACER_OR_WBR,
            true, false, false);

    public static final ElementName CLIPPATH = new ElementName("clippath",
            "clipPath", TreeBuilder.OTHER, false, false, false);

    public static final ElementName CODOMAIN = new ElementName("codomain",
            "codomain", TreeBuilder.OTHER, false, false, false);

    public static final ElementName COLGROUP = new ElementName("colgroup",
            "colgroup", TreeBuilder.COLGROUP, true, false, false);

    public static final ElementName DATAGRID = new ElementName("datagrid",
            "datagrid", TreeBuilder.OTHER, false, false, false);

    public static final ElementName EMPTYSET = new ElementName("emptyset",
            "emptyset", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FACTOROF = new ElementName("factorof",
            "factorof", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FIELDSET = new ElementName("fieldset",
            "fieldset", TreeBuilder.FIELDSET_OR_ADDRESS_OR_DIR, true, false,
            false);

    public static final ElementName FRAMESET = new ElementName("frameset",
            "frameset", TreeBuilder.FRAMESET, true, false, false);

    public static final ElementName FEOFFSET = new ElementName("feoffset",
            "feOffset", TreeBuilder.OTHER, false, false, false);

    public static final ElementName GLYPHREF = new ElementName("glyphref",
            "glyphRef", TreeBuilder.OTHER, false, false, false);

    public static final ElementName INTERVAL = new ElementName("interval",
            "interval", TreeBuilder.OTHER, false, false, false);

    public static final ElementName INTEGERS = new ElementName("integers",
            "integers", TreeBuilder.OTHER, false, false, false);

    public static final ElementName INFINITY = new ElementName("infinity",
            "infinity", TreeBuilder.OTHER, false, false, false);

    public static final ElementName LISTENER = new ElementName("listener",
            "listener", TreeBuilder.OTHER, false, false, false);

    public static final ElementName LOWLIMIT = new ElementName("lowlimit",
            "lowlimit", TreeBuilder.OTHER, false, false, false);

    public static final ElementName METADATA = new ElementName("metadata",
            "metadata", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MENCLOSE = new ElementName("menclose",
            "menclose", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MPHANTOM = new ElementName("mphantom",
            "mphantom", TreeBuilder.OTHER, false, false, false);

    public static final ElementName NOFRAMES = new ElementName("noframes",
            "noframes", TreeBuilder.NOFRAMES, true, false, false);

    public static final ElementName NOSCRIPT = new ElementName("noscript",
            "noscript", TreeBuilder.NOSCRIPT, true, false, false);

    public static final ElementName OPTGROUP = new ElementName("optgroup",
            "optgroup", TreeBuilder.OPTGROUP, true, false, false);

    public static final ElementName POLYLINE = new ElementName("polyline",
            "polyline", TreeBuilder.OTHER, false, false, false);

    public static final ElementName PREFETCH = new ElementName("prefetch",
            "prefetch", TreeBuilder.OTHER, false, false, false);

    public static final ElementName PROGRESS = new ElementName("progress",
            "progress", TreeBuilder.OTHER, false, false, false);

    public static final ElementName PRSUBSET = new ElementName("prsubset",
            "prsubset", TreeBuilder.OTHER, false, false, false);

    public static final ElementName QUOTIENT = new ElementName("quotient",
            "quotient", TreeBuilder.OTHER, false, false, false);

    public static final ElementName SELECTOR = new ElementName("selector",
            "selector", TreeBuilder.OTHER, false, false, false);

    public static final ElementName TEXTAREA = new ElementName("textarea",
            "textarea", TreeBuilder.TEXTAREA, true, false, false);

    public static final ElementName TEXTPATH = new ElementName("textpath",
            "textPath", TreeBuilder.OTHER, false, false, false);

    public static final ElementName VARIANCE = new ElementName("variance",
            "variance", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ANIMATION = new ElementName("animation",
            "animation", TreeBuilder.OTHER, false, false, false);

    public static final ElementName CONJUGATE = new ElementName("conjugate",
            "conjugate", TreeBuilder.OTHER, false, false, false);

    public static final ElementName CONDITION = new ElementName("condition",
            "condition", TreeBuilder.OTHER, false, false, false);

    public static final ElementName COMPLEXES = new ElementName("complexes",
            "complexes", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FONT_FACE = new ElementName("font-face",
            "font-face", TreeBuilder.OTHER, false, false, false);

    public static final ElementName FACTORIAL = new ElementName("factorial",
            "factorial", TreeBuilder.OTHER, false, false, false);

    public static final ElementName INTERSECT = new ElementName("intersect",
            "intersect", TreeBuilder.OTHER, false, false, false);

    public static final ElementName IMAGINARY = new ElementName("imaginary",
            "imaginary", TreeBuilder.OTHER, false, false, false);

    public static final ElementName LAPLACIAN = new ElementName("laplacian",
            "laplacian", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MATRIXROW = new ElementName("matrixrow",
            "matrixrow", TreeBuilder.OTHER, false, false, false);

    public static final ElementName NOTSUBSET = new ElementName("notsubset",
            "notsubset", TreeBuilder.OTHER, false, false, false);

    public static final ElementName OTHERWISE = new ElementName("otherwise",
            "otherwise", TreeBuilder.OTHER, false, false, false);

    public static final ElementName PIECEWISE = new ElementName("piecewise",
            "piecewise", TreeBuilder.OTHER, false, false, false);

    public static final ElementName PLAINTEXT = new ElementName("plaintext",
            "plaintext", TreeBuilder.PLAINTEXT, true, false, false);

    public static final ElementName RATIONALS = new ElementName("rationals",
            "rationals", TreeBuilder.OTHER, false, false, false);

    public static final ElementName SEMANTICS = new ElementName("semantics",
            "semantics", TreeBuilder.OTHER, false, false, false);

    public static final ElementName TRANSPOSE = new ElementName("transpose",
            "transpose", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ANNOTATION = new ElementName("annotation",
            "annotation", TreeBuilder.OTHER, false, false, false);

    public static final ElementName BLOCKQUOTE = new ElementName("blockquote",
            "blockquote", TreeBuilder.DIV_OR_BLOCKQUOTE_OR_CENTER_OR_MENU,
            true, false, false);

    public static final ElementName DIVERGENCE = new ElementName("divergence",
            "divergence", TreeBuilder.OTHER, false, false, false);

    public static final ElementName EULERGAMMA = new ElementName("eulergamma",
            "eulergamma", TreeBuilder.OTHER, false, false, false);

    public static final ElementName EQUIVALENT = new ElementName("equivalent",
            "equivalent", TreeBuilder.OTHER, false, false, false);

    public static final ElementName IMAGINARYI = new ElementName("imaginaryi",
            "imaginaryi", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MALIGNMARK = new ElementName("malignmark",
            "malignmark", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MUNDEROVER = new ElementName("munderover",
            "munderover", TreeBuilder.OTHER, false, false, false);

    public static final ElementName MLABELEDTR = new ElementName("mlabeledtr",
            "mlabeledtr", TreeBuilder.OTHER, false, false, false);

    public static final ElementName NOTANUMBER = new ElementName("notanumber",
            "notanumber", TreeBuilder.OTHER, false, false, false);

    public static final ElementName SOLIDCOLOR = new ElementName("solidcolor",
            "solidColor", TreeBuilder.OTHER, false, false, false);

    public static final ElementName ALTGLYPHDEF = new ElementName(
            "altglyphdef", "altGlyphDef", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName DETERMINANT = new ElementName(
            "determinant", "determinant", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName FEMERGENODE = new ElementName(
            "femergenode", "feMergeNode", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName FECOMPOSITE = new ElementName(
            "fecomposite", "feComposite", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName FESPOTLIGHT = new ElementName(
            "fespotlight", "feSpotLight", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName MALIGNGROUP = new ElementName(
            "maligngroup", "maligngroup", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName MPRESCRIPTS = new ElementName(
            "mprescripts", "mprescripts", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName MOMENTABOUT = new ElementName(
            "momentabout", "momentabout", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName NOTPRSUBSET = new ElementName(
            "notprsubset", "notprsubset", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName PARTIALDIFF = new ElementName(
            "partialdiff", "partialdiff", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName ALTGLYPHITEM = new ElementName(
            "altglyphitem", "altGlyphItem", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName ANIMATECOLOR = new ElementName(
            "animatecolor", "animateColor", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName DATATEMPLATE = new ElementName(
            "datatemplate", "datatemplate", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName EVENT_SOURCE = new ElementName(
            "event-source", "event-source", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName EXPONENTIALE = new ElementName(
            "exponentiale", "exponentiale", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName FETURBULENCE = new ElementName(
            "feturbulence", "feTurbulence", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName FEPOINTLIGHT = new ElementName(
            "fepointlight", "fePointLight", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName FEMORPHOLOGY = new ElementName(
            "femorphology", "feMorphology", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName OUTERPRODUCT = new ElementName(
            "outerproduct", "outerproduct", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName ANIMATEMOTION = new ElementName(
            "animatemotion", "animateMotion", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName COLOR_PROFILE = new ElementName(
            "color-profile", "color-profile", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName FONT_FACE_SRC = new ElementName(
            "font-face-src", "font-face-src", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName FONT_FACE_URI = new ElementName(
            "font-face-uri", "font-face-uri", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName FOREIGNOBJECT = new ElementName(
            "foreignobject", "foreignObject", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName FECOLORMATRIX = new ElementName(
            "fecolormatrix", "feColorMatrix", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName MISSING_GLYPH = new ElementName(
            "missing-glyph", "missing-glyph", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName MMULTISCRIPTS = new ElementName(
            "mmultiscripts", "mmultiscripts", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName SCALARPRODUCT = new ElementName(
            "scalarproduct", "scalarproduct", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName VECTORPRODUCT = new ElementName(
            "vectorproduct", "vectorproduct", TreeBuilder.OTHER, false, false,
            false);

    public static final ElementName ANNOTATION_XML = new ElementName(
            "annotation-xml", "annotation-xml", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName DEFINITION_SRC = new ElementName(
            "definition-src", "definition-src", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName FONT_FACE_NAME = new ElementName(
            "font-face-name", "font-face-name", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName FEGAUSSIANBLUR = new ElementName(
            "fegaussianblur", "feGaussianBlur", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName FEDISTANTLIGHT = new ElementName(
            "fedistantlight", "feDistantLight", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName LINEARGRADIENT = new ElementName(
            "lineargradient", "linearGradient", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName NATURALNUMBERS = new ElementName(
            "naturalnumbers", "naturalnumbers", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName RADIALGRADIENT = new ElementName(
            "radialgradient", "radialGradient", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName ANIMATETRANSFORM = new ElementName(
            "animatetransform", "animateTransform", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName CARTESIANPRODUCT = new ElementName(
            "cartesianproduct", "cartesianproduct", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName FONT_FACE_FORMAT = new ElementName(
            "font-face-format", "font-face-format", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName FECONVOLVEMATRIX = new ElementName(
            "feconvolvematrix", "feConvolveMatrix", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName FEDIFFUSELIGHTING = new ElementName(
            "fediffuselighting", "feDiffuseLighting", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName FEDISPLACEMENTMAP = new ElementName(
            "fedisplacementmap", "feDisplacementMap", TreeBuilder.OTHER, false,
            false, false);

    public static final ElementName FESPECULARLIGHTING = new ElementName(
            "fespecularlighting", "feSpecularLighting", TreeBuilder.OTHER,
            false, false, false);

    public static final ElementName DOMAINOFAPPLICATION = new ElementName(
            "domainofapplication", "domainofapplication", TreeBuilder.OTHER,
            false, false, false);

    public static final ElementName FECOMPONENTTRANSFER = new ElementName(
            "fecomponenttransfer", "feComponentTransfer", TreeBuilder.OTHER,
            false, false, false);

    private final static ElementName[] ELEMENT_NAMES = { ElementName.A,
            ElementName.B, ElementName.G, ElementName.I, ElementName.P,
            ElementName.Q, ElementName.S, ElementName.U, ElementName.BR,
            ElementName.CI, ElementName.CN, ElementName.DD, ElementName.DL,
            ElementName.DT, ElementName.EM, ElementName.EQ, ElementName.FN,
            ElementName.H1, ElementName.H2, ElementName.H3, ElementName.H4,
            ElementName.H5, ElementName.H6, ElementName.GT, ElementName.HR,
            ElementName.IN, ElementName.LI, ElementName.LN, ElementName.LT,
            ElementName.MI, ElementName.MN, ElementName.MO, ElementName.MS,
            ElementName.OL, ElementName.OR, ElementName.PI, ElementName.TD,
            ElementName.TH, ElementName.TR, ElementName.TT, ElementName.UL,
            ElementName.AND, ElementName.ARG, ElementName.ABS, ElementName.BIG,
            ElementName.BDO, ElementName.CSC, ElementName.COL, ElementName.COS,
            ElementName.COT, ElementName.DEL, ElementName.DFN, ElementName.DIR,
            ElementName.DIV, ElementName.EXP, ElementName.GCD, ElementName.GEQ,
            ElementName.IMG, ElementName.INS, ElementName.INT, ElementName.KBD,
            ElementName.LOG, ElementName.LCM, ElementName.LEQ, ElementName.MTD,
            ElementName.MIN, ElementName.MAP, ElementName.MTR, ElementName.MAX,
            ElementName.NEQ, ElementName.NOT, ElementName.NAV, ElementName.PRE,
            ElementName.REM, ElementName.SUB, ElementName.SEC, ElementName.SVG,
            ElementName.SUM, ElementName.SIN, ElementName.SEP, ElementName.SUP,
            ElementName.SET, ElementName.TAN, ElementName.USE, ElementName.VAR,
            ElementName.WBR, ElementName.XMP, ElementName.XOR,
            ElementName.AREA, ElementName.ABBR, ElementName.BASE,
            ElementName.BVAR, ElementName.BODY, ElementName.CARD,
            ElementName.CODE, ElementName.CITE, ElementName.CSCH,
            ElementName.COSH, ElementName.COTH, ElementName.CURL,
            ElementName.DESC, ElementName.DIFF, ElementName.DEFS,
            ElementName.FORM, ElementName.FONT, ElementName.GRAD,
            ElementName.HEAD, ElementName.HTML, ElementName.LINE,
            ElementName.LINK, ElementName.LIST, ElementName.META,
            ElementName.MSUB, ElementName.MODE, ElementName.MATH,
            ElementName.MARK, ElementName.MASK, ElementName.MEAN,
            ElementName.MSUP, ElementName.MENU, ElementName.MROW,
            ElementName.NONE, ElementName.NOBR, ElementName.NEST,
            ElementName.PATH, ElementName.PLUS, ElementName.RULE,
            ElementName.REAL, ElementName.RELN, ElementName.RECT,
            ElementName.ROOT, ElementName.RUBY, ElementName.SECH,
            ElementName.SINH, ElementName.SPAN, ElementName.SAMP,
            ElementName.STOP, ElementName.SDEV, ElementName.TIME,
            ElementName.TRUE, ElementName.TREF, ElementName.TANH,
            ElementName.TEXT, ElementName.VIEW, ElementName.ASIDE,
            ElementName.AUDIO, ElementName.APPLY, ElementName.EMBED,
            ElementName.FRAME, ElementName.FALSE, ElementName.FLOOR,
            ElementName.GLYPH, ElementName.HKERN, ElementName.IMAGE,
            ElementName.IDENT, ElementName.INPUT, ElementName.LABEL,
            ElementName.LIMIT, ElementName.MFRAC, ElementName.MPATH,
            ElementName.METER, ElementName.MOVER, ElementName.MINUS,
            ElementName.MROOT, ElementName.MSQRT, ElementName.MTEXT,
            ElementName.NOTIN, ElementName.PIECE, ElementName.PARAM,
            ElementName.POWER, ElementName.REALS, ElementName.STYLE,
            ElementName.SMALL, ElementName.THEAD, ElementName.TABLE,
            ElementName.TITLE, ElementName.TSPAN, ElementName.TIMES,
            ElementName.TFOOT, ElementName.TBODY, ElementName.UNION,
            ElementName.VKERN, ElementName.VIDEO, ElementName.ARCSEC,
            ElementName.ARCCSC, ElementName.ARCTAN, ElementName.ARCSIN,
            ElementName.ARCCOS, ElementName.APPLET, ElementName.ARCCOT,
            ElementName.APPROX, ElementName.BUTTON, ElementName.CIRCLE,
            ElementName.CENTER, ElementName.CURSOR, ElementName.CANVAS,
            ElementName.DIVIDE, ElementName.DEGREE, ElementName.DIALOG,
            ElementName.DOMAIN, ElementName.EXISTS, ElementName.FETILE,
            ElementName.FIGURE, ElementName.FORALL, ElementName.FILTER,
            ElementName.FOOTER, ElementName.HEADER, ElementName.IFRAME,
            ElementName.KEYGEN, ElementName.LAMBDA, ElementName.LEGEND,
            ElementName.MSPACE, ElementName.MTABLE, ElementName.MSTYLE,
            ElementName.MGLYPH, ElementName.MEDIAN, ElementName.MUNDER,
            ElementName.MARKER, ElementName.MERROR, ElementName.MOMENT,
            ElementName.MATRIX, ElementName.OPTION, ElementName.OBJECT,
            ElementName.OUTPUT, ElementName.PRIMES, ElementName.SOURCE,
            ElementName.STRIKE, ElementName.STRONG, ElementName.SWITCH,
            ElementName.SYMBOL, ElementName.SPACER, ElementName.SELECT,
            ElementName.SUBSET, ElementName.SCRIPT, ElementName.TBREAK,
            ElementName.VECTOR, ElementName.ARTICLE, ElementName.ANIMATE,
            ElementName.ARCSECH, ElementName.ARCCSCH, ElementName.ARCTANH,
            ElementName.ARCSINH, ElementName.ARCCOSH, ElementName.ARCCOTH,
            ElementName.ACRONYM, ElementName.ADDRESS, ElementName.BGSOUND,
            ElementName.COMMAND, ElementName.COMPOSE, ElementName.CEILING,
            ElementName.CSYMBOL, ElementName.CAPTION, ElementName.DISCARD,
            ElementName.DECLARE, ElementName.DETAILS, ElementName.ELLIPSE,
            ElementName.FEFUNCA, ElementName.FEFUNCB, ElementName.FEBLEND,
            ElementName.FEFLOOD, ElementName.FEIMAGE, ElementName.FEMERGE,
            ElementName.FEFUNCG, ElementName.FEFUNCR, ElementName.HANDLER,
            ElementName.INVERSE, ElementName.IMPLIES, ElementName.ISINDEX,
            ElementName.LOGBASE, ElementName.LISTING, ElementName.MFENCED,
            ElementName.MPADDED, ElementName.MARQUEE, ElementName.MACTION,
            ElementName.MSUBSUP, ElementName.NOEMBED, ElementName.POLYGON,
            ElementName.PATTERN, ElementName.PRODUCT, ElementName.SETDIFF,
            ElementName.SECTION, ElementName.TENDSTO, ElementName.UPLIMIT,
            ElementName.ALTGLYPH, ElementName.BASEFONT, ElementName.CLIPPATH,
            ElementName.CODOMAIN, ElementName.COLGROUP, ElementName.DATAGRID,
            ElementName.EMPTYSET, ElementName.FACTOROF, ElementName.FIELDSET,
            ElementName.FRAMESET, ElementName.FEOFFSET, ElementName.GLYPHREF,
            ElementName.INTERVAL, ElementName.INTEGERS, ElementName.INFINITY,
            ElementName.LISTENER, ElementName.LOWLIMIT, ElementName.METADATA,
            ElementName.MENCLOSE, ElementName.MPHANTOM, ElementName.NOFRAMES,
            ElementName.NOSCRIPT, ElementName.OPTGROUP, ElementName.POLYLINE,
            ElementName.PREFETCH, ElementName.PROGRESS, ElementName.PRSUBSET,
            ElementName.QUOTIENT, ElementName.SELECTOR, ElementName.TEXTAREA,
            ElementName.TEXTPATH, ElementName.VARIANCE, ElementName.ANIMATION,
            ElementName.CONJUGATE, ElementName.CONDITION,
            ElementName.COMPLEXES, ElementName.FONT_FACE,
            ElementName.FACTORIAL, ElementName.INTERSECT,
            ElementName.IMAGINARY, ElementName.LAPLACIAN,
            ElementName.MATRIXROW, ElementName.NOTSUBSET,
            ElementName.OTHERWISE, ElementName.PIECEWISE,
            ElementName.PLAINTEXT, ElementName.RATIONALS,
            ElementName.SEMANTICS, ElementName.TRANSPOSE,
            ElementName.ANNOTATION, ElementName.BLOCKQUOTE,
            ElementName.DIVERGENCE, ElementName.EULERGAMMA,
            ElementName.EQUIVALENT, ElementName.IMAGINARYI,
            ElementName.MALIGNMARK, ElementName.MUNDEROVER,
            ElementName.MLABELEDTR, ElementName.NOTANUMBER,
            ElementName.SOLIDCOLOR, ElementName.ALTGLYPHDEF,
            ElementName.DETERMINANT, ElementName.FEMERGENODE,
            ElementName.FECOMPOSITE, ElementName.FESPOTLIGHT,
            ElementName.MALIGNGROUP, ElementName.MPRESCRIPTS,
            ElementName.MOMENTABOUT, ElementName.NOTPRSUBSET,
            ElementName.PARTIALDIFF, ElementName.ALTGLYPHITEM,
            ElementName.ANIMATECOLOR, ElementName.DATATEMPLATE,
            ElementName.EVENT_SOURCE, ElementName.EXPONENTIALE,
            ElementName.FETURBULENCE, ElementName.FEPOINTLIGHT,
            ElementName.FEMORPHOLOGY, ElementName.OUTERPRODUCT,
            ElementName.ANIMATEMOTION, ElementName.COLOR_PROFILE,
            ElementName.FONT_FACE_SRC, ElementName.FONT_FACE_URI,
            ElementName.FOREIGNOBJECT, ElementName.FECOLORMATRIX,
            ElementName.MISSING_GLYPH, ElementName.MMULTISCRIPTS,
            ElementName.SCALARPRODUCT, ElementName.VECTORPRODUCT,
            ElementName.ANNOTATION_XML, ElementName.DEFINITION_SRC,
            ElementName.FONT_FACE_NAME, ElementName.FEGAUSSIANBLUR,
            ElementName.FEDISTANTLIGHT, ElementName.LINEARGRADIENT,
            ElementName.NATURALNUMBERS, ElementName.RADIALGRADIENT,
            ElementName.ANIMATETRANSFORM, ElementName.CARTESIANPRODUCT,
            ElementName.FONT_FACE_FORMAT, ElementName.FECONVOLVEMATRIX,
            ElementName.FEDIFFUSELIGHTING, ElementName.FEDISPLACEMENTMAP,
            ElementName.FESPECULARLIGHTING, ElementName.DOMAINOFAPPLICATION,
            ElementName.FECOMPONENTTRANSFER, };

    private final static int[] ELEMENT_HASHES = { 1057, 1090, 1255, 1321, 1552,
            1585, 1651, 1717, 68162, 68899, 69059, 69764, 70020, 70276, 71077,
            71205, 72134, 72232, 72264, 72296, 72328, 72360, 72392, 73351,
            74312, 75209, 78124, 78284, 78476, 79149, 79309, 79341, 79469,
            81295, 81487, 82224, 86164, 86292, 86612, 86676, 87445, 3183041,
            3186241, 3198017, 3218722, 3226754, 3247715, 3256803, 3263971,
            3264995, 3289252, 3291332, 3295524, 3299620, 3326725, 3379303,
            3392679, 3448233, 3460553, 3461577, 3510347, 3546604, 3552364,
            3556524, 3576461, 3586349, 3588141, 3590797, 3596333, 3622062,
            3625454, 3627054, 3675728, 3749042, 3771059, 3771571, 3776211,
            3782323, 3782963, 3784883, 3785395, 3788979, 3815476, 3839605,
            3885110, 3917911, 3948984, 3951096, 135304769, 135858241,
            136498210, 136906434, 137138658, 137512995, 137531875, 137548067,
            137629283, 137645539, 137646563, 137775779, 138529956, 138615076,
            139040932, 140954086, 141179366, 141690439, 142738600, 143013512,
            146979116, 147175724, 147475756, 147902637, 147936877, 148017645,
            148131885, 148228141, 148229165, 148309165, 148395629, 148551853,
            148618829, 149076462, 149490158, 149572782, 151277616, 151639440,
            153268914, 153486514, 153563314, 153750706, 153763314, 153914034,
            154406067, 154417459, 154600979, 154678323, 154680979, 154866835,
            155366708, 155375188, 155391572, 155465780, 155869364, 158045494,
            168988979, 169321621, 169652752, 173151309, 174240818, 174247297,
            174669292, 175391532, 176638123, 177380397, 177879204, 177886734,
            180753473, 181020073, 181503558, 181686320, 181999237, 181999311,
            182048201, 182074866, 182078003, 182083764, 182920847, 184716457,
            184976961, 185145071, 187281445, 187872052, 188100653, 188875944,
            188919873, 188920457, 189203987, 189371817, 189414886, 189567458,
            190266670, 191318187, 191337609, 202479203, 202493027, 202835587,
            202843747, 203013219, 203036048, 203045987, 203177552, 203898516,
            204648562, 205067918, 205078130, 205096654, 205689142, 205690439,
            205766017, 205988909, 207213161, 207794484, 207800999, 208023602,
            208213644, 208213647, 210310273, 210940978, 213325049, 213946445,
            214055079, 215125040, 215134273, 215135028, 215237420, 215418148,
            215553166, 215553394, 215563858, 215627949, 215754324, 217529652,
            217713834, 217732628, 218731945, 221417045, 221424946, 221493746,
            221515401, 221658189, 221844577, 221908140, 221910626, 221921586,
            222659762, 225001091, 236105833, 236113965, 236194995, 236195427,
            236206132, 236206387, 236211683, 236212707, 236381647, 236571826,
            237124271, 238172205, 238210544, 238270764, 238435405, 238501172,
            239224867, 239257644, 239710497, 240307721, 241208789, 241241557,
            241318060, 241319404, 241343533, 241344069, 241405397, 241765845,
            243864964, 244502085, 244946220, 245109902, 247647266, 247707956,
            248648814, 248648836, 248682161, 248986932, 249058914, 249697357,
            252132601, 252135604, 252317348, 255007012, 255278388, 256365156,
            257566121, 269763372, 271202790, 271863856, 272049197, 272127474,
            272770631, 274339449, 274939471, 275388004, 275388005, 275388006,
            275977800, 278267602, 278513831, 278712622, 281613765, 281683369,
            282120228, 282250732, 282508942, 283743649, 283787570, 284710386,
            285391148, 285478533, 285854898, 285873762, 286931113, 288964227,
            289445441, 289689648, 291671489, 303512884, 305319975, 305610036,
            305764101, 308448294, 308675890, 312085683, 312264750, 315032867,
            316391000, 317331042, 317902135, 318950711, 319447220, 321499182,
            322538804, 323145200, 337067316, 337826293, 339905989, 340833697,
            341457068, 345302593, 349554733, 349771471, 349786245, 350819405,
            356072847, 370349192, 373962798, 375558638, 375574835, 376053993,
            383276530, 383373833, 383407586, 384439906, 386079012, 404133513,
            404307343, 407031852, 408063573, 408072233, 409112005, 409608425,
            409771500, 419040932, 437730612, 439529766, 442616365, 442813037,
            443157674, 443295316, 450118444, 450482697, 456789668, 459935396,
            471217869, 474073645, 476230702, 476665218, 476717289, 483014825,
            485083298, 489306281, 538364390, 540675748, 543819186, 543958612,
            576960820, 577242548, 610515252, 642202932, 644420819, };

}
