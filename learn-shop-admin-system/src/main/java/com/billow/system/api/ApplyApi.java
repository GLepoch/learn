package com.billow.system.api;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.vo.CustomPage;
import com.billow.base.workflow.vo.TaskVo;
import com.billow.system.pojo.vo.ApplyInfoVo;
import com.billow.system.pojo.vo.LeaveVo;
import com.billow.system.service.ApplyInfoService;
import com.billow.tools.enums.ApplyTypeEnum;
import com.billow.tools.utlis.UserTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 申请操作
 *
 * @author liuyongtao
 * @create 2019-09-06 11:22
 */
@Slf4j
@RestController
@RequestMapping("/applyApi")
@Api(value = "申请操作API")
public class ApplyApi {

    @Autowired
    private WorkFlowExecute workFlowExecute;
    @Autowired
    private WorkFlowQuery workFlowQuery;
    @Autowired
    private UserTools userTools;
    @Autowired
    private ApplyInfoService applyInfoService;

    @ApiOperation(value = "查询个人任务列表")
    @PostMapping("/queryMyTaskList")
    public CustomPage queryMyTaskList(@RequestBody ApplyInfoVo applyInfoVo) {
        String currentUserCode = userTools.getCurrentUserCode();
        applyInfoVo.setAssignee(currentUserCode);
        CustomPage applyInfoVoPage = applyInfoService.queryMyTaskList(applyInfoVo, applyInfoVo.getOffset(), applyInfoVo.getPageSize());
        return applyInfoVoPage;
    }

    @ApiOperation(value = "查询个人任务数量")
    @GetMapping("/queryAssigneeTaskCount")
    public long queryAssigneeTaskCount() {
        String currentUserCode = userTools.getCurrentUserCode();
        TaskVo taskVo = new TaskVo();
        taskVo.setAssignee(currentUserCode);
        long count = workFlowQuery.queryAssigneeTaskCount(taskVo);
        return count;
    }

    @ApiOperation(value = "我发起的流程（所有的）")
    @GetMapping("/myStartProdeList")
    public Page myStartProdeList(@RequestBody ApplyInfoVo applyInfoVo) {
        String currentUserCode = userTools.getCurrentUserCode();
        applyInfoVo.setAssignee(currentUserCode);
        Page applyInfoVoPage = applyInfoService.myStartProdeList(applyInfoVo);
        return applyInfoVoPage;
    }

    @ApiOperation(value = "我发起的流程（所有的）")
    @GetMapping("/myStartProdeCount")
    public long myStartProdeCount() {
        String currentUserCode = userTools.getCurrentUserCode();
        long count = workFlowQuery.queryMyStartProdeAllCount(currentUserCode);
        return count;
    }

    @ApiOperation(value = "运行中的的流程")
    @GetMapping("/ongoingCount")
    public long ongoingCount() {
        String currentUserCode = userTools.getCurrentUserCode();
        long count = workFlowQuery.queryMyStartProdeActiveCount(currentUserCode);
        return count;
    }

    @ApiOperation(value = "认领任务")
    @PostMapping("/claimTask/{taskId}")
    public void claimTask(@PathVariable String taskId) {
        String currentUserCode = userTools.getCurrentUserCode();
        workFlowExecute.claim(taskId, currentUserCode);
    }

//    @ApiOperation(value = "放弃认领任务")
//    @PostMapping("/unclaimTask/{taskId}")
//    public void unclaimTask(@PathVariable String taskId) {
//        workFlowExecute.unclaim(taskId);
//    }

//    @ApiOperation(value = "查询任务列表")
//    @PostMapping("/queryTaskList")
//    public Page<TaskVo> queryTaskList(@RequestBody TaskVo taskVo) {
//        Page<TaskVo> taskVos = workFlowQuery.queryTaskList(taskVo, taskVo.getOffset(), taskVo.getPageSize());
//        return taskVos;
//    }

    @ApiOperation(value = "查看活动的流程图（显示运行轨迹）")
    @GetMapping("/viewExecutionImgById/{executionId}")
    public void viewDeployImgById(@PathVariable String executionId, HttpServletResponse response) throws Exception {
        workFlowQuery.genActiveProccessImage(executionId, response);
    }

    @ApiOperation(value = "删除已经结束的申请")
    @DeleteMapping("/deleteApplyInfoById/{id}")
    public void submitLeave(@PathVariable Long id) {
        applyInfoService.deleteApplyInfoById(id);
    }

    @ApiOperation(value = "提交请假任务")
    @PostMapping("/commitLeaveProcess/{taskId}")
    public void commitLeaveProcess(@PathVariable("taskId") String taskId,
                              @RequestBody Map<String, Object> variables) {
        workFlowExecute.commitProcess(taskId, variables);
    }

    @ApiOperation(value = "提交请假申请")
    @PostMapping("/submitLeave")
    public void submitLeave(@RequestBody LeaveVo leaveVo) {
        String operator = userTools.getCurrentUserCode();
        applyInfoService.submitApplyInfo(operator, ApplyTypeEnum.LEAVE, leaveVo);
    }

}