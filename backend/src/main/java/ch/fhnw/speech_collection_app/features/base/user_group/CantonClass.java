package ch.fhnw.speech_collection_app.features.base.user_group;

public class CantonClass {

    public static String enumToCountyId(CantonEnum canton) {
        switch (canton) {
            case CH_AG:
                return "ag";
            case CH_AI:
                return "ai";
            case CH_AR:
                return "ar";
            case CH_BE:
                return "be";
            case CH_BL:
                return "bl";
            case CH_BS:
                return "bs";
            case CH_FR:
                return "fr";
            case CH_GE:
                return "ge";
            case CH_GL:
                return "gl";
            case CH_GR:
                return "gr";
            case CH_JU:
                return "ju";
            case CH_LU:
                return "lu";
            case CH_NE:
                return "ne";
            case CH_NW:
                return "nw";
            case CH_OW:
                return "ow";
            case CH_SG:
                return "sg";
            case CH_SH:
                return "sh";
            case CH_SO:
                return "so";
            case CH_SZ:
                return "sz";
            case CH_TG:
                return "tg";
            case CH_TI:
                return "ti";
            case CH_UR:
                return "ur";
            case CH_VD:
                return "vd";
            case CH_VS:
                return "vs";
            case CH_ZG:
                return "zg";
            case CH_ZH:
                return "zh";
            default:
                return "";
        }
    }

    public enum CantonEnum {
        CH_AG, CH_AI, CH_AR, CH_BL, CH_BE, CH_BS, CH_FR, CH_GE, CH_GL, CH_GR, CH_JU, CH_LU, CH_NE, CH_NW, CH_OW, CH_SG,
        CH_SH, CH_SO, CH_SZ, CH_TG, CH_TI, CH_UR, CH_VD, CH_VS, CH_ZG, CH_ZH,
    }
}
