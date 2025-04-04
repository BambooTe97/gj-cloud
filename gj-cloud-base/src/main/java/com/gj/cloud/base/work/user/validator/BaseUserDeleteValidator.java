//package com.gj.cloud.base.work.user.validator;
//
//import java.util.List;
//
//import com.gj.cloud.base.work.user.validator.validation.Validator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BaseUserDeleteValidator implements Validator {
//    @Autowired
//    @Lazy
//    private BaseRoleUserService baseRoleUserService;
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public void validate(Object... args) {
//        List<String> userIdList = (List<String>) args[0];
//        // 是否添加到角色
//        List<BaseRoleUserBean> baseRoleUserList = baseRoleUserService.selectListByFilter(SearchFilter.filter(MatchPattern.OR, "USERID", userIdList));
//        if (!baseRoleUserList.isEmpty()) {
//            String userId = baseRoleUserList.get(0).getUserId();
//            throw new ApplicationRuntimeException("BASE.USER.TIP.USER_LINKED_WITH_ROLE_CAN_NOT_DELETE", userId);
//        }
//    }
//
//}
