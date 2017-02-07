
import java.util.Date;

/**
 * Created by alexmengshede on 16/12/4.
 */
public class ReturnGoodList {
    private Byte qty;
    private String name;
    private String serial_number;
    private String price;
    private String volume;

    public ReturnGoodList() {
    }

    public ReturnGoodList(Byte qty, String name) {
        this.qty = qty;
        this.name = name;
    }

    public ReturnGoodList(Byte qty, String name, String serial_number) {
        this.qty = qty;
        this.name = name;
        this.serial_number = serial_number;
    }

    public ReturnGoodList(Byte qty, String name, String serial_number, String price) {
        this.qty = qty;
        this.name = name;
        this.serial_number = serial_number;
        this.price = price;
    }

    public ReturnGoodList(Byte qty, String name, String serial_number, String price, String volume) {
        this.qty = qty;
        this.name = name;
        this.serial_number = serial_number;
        this.price = price;
        this.volume = volume;
    }

    public Byte getQty() {
        return qty;
    }

    public void setQty(Byte qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
