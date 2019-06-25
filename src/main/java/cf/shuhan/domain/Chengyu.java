package cf.shuhan.domain;

import lombok.Data;

@Data
public class Chengyu {
    private String word;
    private String pinyin;
    //出处
    private String derivation;
    //解释
    private String explanation;
    //示例
    private String example;

}