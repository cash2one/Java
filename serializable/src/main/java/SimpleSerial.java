import java.io.*;

/**
 * Created by Administrator on 2016/8/8.
 */

public class SimpleSerial {

    public static void main(String[] args) throws Exception {
        File file = new File("person.out");

        ObjectOutputStream oout = new ObjectOutputStream( new FileOutputStream(file) );
        Person person = new Person("John", 101, Gender.MALE);
        oout.writeObject(person);
        oout.close();

        ObjectInputStream oin = new ObjectInputStream( new FileInputStream(file) );
        Object newPerson = oin.readObject();
        oin.close();

        System.out.println("newPerson: " + newPerson);

        String field = "numid, userid, ip, mac, province, action, platform, series, version, adid, create_date";
        String [] stringArr= field.split(",");

        System.out.println("stringArr: " + stringArr[3]);

    }
}
