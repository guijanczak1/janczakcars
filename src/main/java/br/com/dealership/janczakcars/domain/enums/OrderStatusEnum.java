package br.com.dealership.janczakcars.domain.enums;

public enum OrderStatusEnum {

    CRIADO(1, "Criado"),
    PENDENTE(2, "Pendente"),
    CANCELADO(3, "Cancelado");

    private final int code;
    private final String description;

    OrderStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static boolean isValidCode(int code) {
        for (OrderStatusEnum status : OrderStatusEnum.values()) {
            if (status.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    public static String getDescriptionByCode(int code) {
        for (OrderStatusEnum status : OrderStatusEnum.values()) {
            if (status.getCode() == code) {
                return status.description;
            }
        }
        return null;
    }
}