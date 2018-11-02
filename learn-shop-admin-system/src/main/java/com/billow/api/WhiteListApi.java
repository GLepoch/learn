package com.billow.api;

import com.billow.common.base.BaseApi;
import com.billow.common.business.whiteList.pojo.vo.WhiteListVo;
import com.billow.common.enums.ResCodeEnum;
import com.billow.common.resData.BaseResponse;
import com.billow.common.business.whiteList.service.WhiteListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 白名单
 *
 * @author liuyongtao
 * @create 2018-05-19 14:35
 */
@Api(value = "WhiteListController", description = "白名单")
@RestController
@RequestMapping("/whiteListApi")
public class WhiteListApi extends BaseApi {

    @Autowired
    private WhiteListService whiteListService;

    /**
     * 根据ip和模块查询出有效白名单
     *
     * @param ip       访问ip
     * @param module   模块
     * @param validInd 有效
     * @return com.billow.common.resData.BaseResponse<java.util.List<com.billow.pojo.vo.sys.WhiteListVo>>
     * @author LiuYongTao
     * @date 2018/5/21 8:35
     */
    @ApiOperation(value = "获取有效的白名单信息", notes = "根据ip和模块获取有效的白名单信息")
    @GetMapping("/findWhiteListVos/{ip}/{module}/{validInd}")
    public BaseResponse<List<WhiteListVo>> findWhiteListVos(@PathVariable("ip") String ip,
                                                            @PathVariable("module") String module,
                                                            @PathVariable("validInd") boolean validInd) {
        BaseResponse<List<WhiteListVo>> baseResponse = new BaseResponse<>();
        try {
            List<WhiteListVo> whiteListVos = whiteListService.findByIpAndModuleAndValidInd(ip, module, validInd);
            baseResponse.setResData(whiteListVos);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResCode(ResCodeEnum.FAIL);
        }
        return baseResponse;
    }
}
