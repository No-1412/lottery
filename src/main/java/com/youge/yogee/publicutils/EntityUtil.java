package com.youge.yogee.publicutils;

/**
 * Created by Administrator on 2017-12-11 0011.实体类生成
 */
public class EntityUtil {
    public static void main(String[] args) {
        createEntity();
    }

    public static void createEntity() {
        String field = "id\n" +
                "name\n" +
                "create_date\n" +
                "del_flag\n" +
                "remarks\n" +
                "content\n";
        String[] str = field.split("\n");
        String end = "";
        for (int i = 0; i < str.length; i++) {
            end = str[i];
            //判断"_"出现的次数
            byte[] temp = end.getBytes();//将此 String 解码为字节序列，并将结果存储到一个新的字节数组中。
            int count = 0;
            //遍历数组的每一个元素，也就是字符串中的每一个字母
            for (int j = 0; j < temp.length; j++) {
                //如果等于 _
                if (temp[j] == '_') {
                    //计数器加一
                    count++;
                }
            }
            //出现几次"_"就循环几次
            for (int j = 0; j < count; j++) {
                if (end.contains("_")) {
                    int index = end.indexOf("_");
                    String original = end.substring(index, index + 2); //获取_和后面的首位字母
                    String after = end.substring(index + 1, index + 2); //将_后面的首位英文单词转换大写字母.
                    end = end.replace(original, after.toUpperCase());
                } else {
                    end = str[i];
                }
            }
            //默认字段都是string类型,包含钱的设置成 BigDecimal类型,其他自己修改
            StringBuffer sbf = null;
            if(end.contains("price") || end.contains("Price")){
                 sbf = new StringBuffer("private BigDecimal ");
            }else{
                 sbf = new StringBuffer("private String ");
            }
            sbf.append(end).append("; //");
            System.out.println(sbf);
        }
    }
}
