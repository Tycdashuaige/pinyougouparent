package com.pinyougou.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangyucong
 * @Title: UserDetailsServiceImpl
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/614:55
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();

        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_SELLER"));

        TbSeller tbSeller = sellerService.findOne(username);

        if (tbSeller != null) {
            if (tbSeller.getStatus().equals("1")) {
                return new User(username, tbSeller.getPassword(), grantedAuthorityList);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
