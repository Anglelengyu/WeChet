package cf.shuhan.mapper;

import cf.shuhan.domain.Chengyu;

import java.util.List;

public interface ChengyuMapper {
    List<Chengyu> getChengyuByName(String lastName);
    Chengyu getOneByrand();
    Chengyu getOneByWord(String word);
}
