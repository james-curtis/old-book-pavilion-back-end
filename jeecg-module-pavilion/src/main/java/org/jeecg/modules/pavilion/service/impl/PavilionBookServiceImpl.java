package org.jeecg.modules.pavilion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.pavilion.entity.PavilionBook;
import org.jeecg.modules.pavilion.mapper.PavilionBookMapper;
import org.jeecg.modules.pavilion.service.IPavilionBookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

/**
 * @Description: 图书
 * @Author: jeecg-boot
 * @Date: 2022-10-20
 * @Version: V1.0
 */
@Service
public class PavilionBookServiceImpl extends ServiceImpl<PavilionBookMapper, PavilionBook> implements
    IPavilionBookService {

    /**
     * 图书信息查询API
     * @param keyword 待查询的关键词
     * @return 书本信息的List
     */
    @Override
    public Result<List<PavilionBook>> getBookInformation(String keyword) {
        keyword = keyword.trim();
        //删除关键字里的'-'以判断是否是ISBN码
        String keywordIsbn = keyword.replace("-", "");
        Matcher isNum = compile("[0-9]*").matcher(keywordIsbn);
        //判断是否为isbn
        if (isNum.matches()) {
            LambdaQueryWrapper<PavilionBook> isbnWrapper = new LambdaQueryWrapper<>();
            isbnWrapper.eq(PavilionBook::getIsbn, keywordIsbn);
            //查询
            PavilionBook book = this.getOne(isbnWrapper);
            if (book != null) {
                ArrayList<PavilionBook> list = new ArrayList<>();
                list.add(book);
                return Result.OK(list);
            }

        } else {
            LambdaQueryWrapper<PavilionBook> keywordWrapper = new LambdaQueryWrapper<>();
            //匹配主题词、作者名、书名、副书名
            keywordWrapper.like(PavilionBook::getKeyword, keyword).or()
                .like(PavilionBook::getAuthorName, keyword).or()
                .like(PavilionBook::getName, keyword).or()
                .like(PavilionBook::getSubtitle, keyword);

            List<PavilionBook> list = this.list(keywordWrapper);

            if (list != null) {
                if (!list.isEmpty()) {
                    return Result.OK(list);
                }
            }
        }
        return Result.error("未查询到这本书");
    }
}
