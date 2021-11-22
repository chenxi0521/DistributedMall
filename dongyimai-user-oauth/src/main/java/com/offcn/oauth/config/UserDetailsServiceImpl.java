package com.offcn.oauth.config;

import com.alibaba.druid.util.StringUtils;
import com.offcn.entity.Result;
import com.offcn.user.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxi
 * @date 2021/11/18 18:58
 * @description
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserFeign userFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Result<com.offcn.user.pojo.User> result = userFeign.findUserByUserName(username);

        if(result!=null && result.getData()!=null){
            com.offcn.user.pojo.User user = result.getData();
            String encode_pwd = user.getPassword();

            // 根据用户名从数据库中查询权限列表

            String authorities = user.getAuthorities();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            if(!StringUtils.isEmpty(authorities)){
                grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
            }

            return new User(username,encode_pwd,grantedAuthorities);
        }

        return null;



    }
}
