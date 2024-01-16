package com.example.bpp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Xiren
 * @description 文档类型枚举类
 */
@Getter
@AllArgsConstructor
public enum FormulaEnum {
    F00001("1月##2-1##台数##国产燃油车小计", "F7+F8+F9+F10+F11+F12+F13+F14+F15+F16+F17+F18+F19+F20+F21+F22+F23+F24+F25+F26+F27+F28+F29+F30+F31", "F34"),
    F00002("1月##2-1##台数##新能源车小计", "F42+F43", "F46"),
    F00003("1月##2-1##台数##合计", "F34+F46", "F48"),
    ;

    private final String index;
    private final String formula;
    private final String coordinate;

    public static FormulaEnum getByIndex(String index) {
        FormulaEnum[] typeEnums = FormulaEnum.values();
        for (FormulaEnum formulaEnum : typeEnums) {
            if (formulaEnum.getIndex().equals(index)) {
                return formulaEnum;
            }
        }
        return null;
    }

    public static FormulaEnum getByCoordinate(String coordinate) {
        FormulaEnum[] typeEnums = FormulaEnum.values();
        for (FormulaEnum formulaEnum : typeEnums) {
            if (formulaEnum.getCoordinate().equals(coordinate)) {
                return formulaEnum;
            }
        }
        return null;
    }
}
