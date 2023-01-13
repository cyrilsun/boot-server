package com.mrsun.bootserver.controller;

import com.mrsun.bootserver.common.Result;
import com.mrsun.bootserver.service.DTO.DictDTO;
import com.mrsun.bootserver.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 字典表控制器
 *
 * @author sunxiaogang
 **/
@Slf4j
@RestController
@RequestMapping("/api")
public class DictController {

    private final DictService dictService;

    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    /**
     * 添加字典表
     *
     * @param dictDTO 添加字典表数据
     * @return 创建的字典表
     */
    @PostMapping("/dicts")
    public Result<?> addDict(@RequestBody @Valid DictDTO dictDTO) {
        log.debug("请求添加字典表:[{}]", dictDTO);
        return Result.success(this.dictService.create(dictDTO));
    }

    /**
     * 更新字典表
     *
     * @param dictDTO 更新字典表数据
     * @return 更新后的字典表
     */
    @PutMapping("/dicts")
    public Result<?> updateDict(@RequestBody @Valid DictDTO dictDTO) {
        log.debug("请求修改字典表:[{}]", dictDTO);
        return Result.success(dictService.updateById(dictDTO));
    }

    /**
     * 查询字典表详情
     *
     * @param id 字典表id
     * @return 字典表信息
     */
    @GetMapping("/dicts/{id}")
    public Result<?> getDict(@PathVariable Integer id) {
        log.debug("请求查询字典表详情:[{}]", id);
        return Result.success(this.dictService.findOneById(id));
    }

    /**
     * 分页查询字典表列表
     *
     * @param dictDTO 查询条件
     * @param page    分页参数
     * @param limit   分页参数
     * @return 分页
     */
    @PostMapping(value = "/dicts/page", params = {"page", "limit"})
    public Result<?> findAllDict(@RequestBody DictDTO dictDTO, Integer page, Integer limit) {
        log.debug("请求分页查询字典表列表:[条件: {}, 分页参数:{},{}]", dictDTO, page, limit);
        return Result.success(dictService.findPage(dictDTO, page, limit));
    }

    /**
     * 删除字典表
     *
     * @param id 字典表ids
     * @return 删除结果
     */
    @DeleteMapping("/dicts/{id}")
    public Result<?> deleteDict(@PathVariable Integer id) {
        log.debug("请求删除字典表:[{}]", id);
        return Result.success(this.dictService.deleteById(id));
    }

    /**
     * 查询下级字典列表
     *
     * @param id 父id
     * @return
     */
    @GetMapping("/dicts/children/{id}")
    public Result<?> getChildrenDicts(@PathVariable Integer id) {
        log.debug("请求查询下级字典列表:{}", id);
        return Result.success(this.dictService.findAll(id));
    }
}
