package com.lsy.test.spring.jpa.repository;

import com.lsy.test.spring.jpa.bean.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TUserRepository extends JpaRepository<TUser, Long> {

//    List<TUser> findAll(TUser exampleBean);


//	@Query(value = "select u.`id`,u.`user_account`,u.`bdp_user`,e.`user_email`,e.`user_name_cn`,e.`user_dept` from big_data_platform.bdp_user_info u " +
//			"left join big_data_platform.bdp_user_extra_info e on(u.`user_account`=e.`user_key`) " +
//			"where u.`bdp_user`= 0 and u.`user_account` like ?1 or e.`user_name_cn` like ?2 ;",nativeQuery = true)
//	public List<Map<String,Object>> matchAccountOrName(String account, String name);
//
//	@Query(value = "select u.`id`,u.`user_account`,u.`bdp_user`,e.`user_email`,e.`user_name_cn`,e.`user_dept` from big_data_platform.bdp_user_info u " +
//			"left join big_data_platform.bdp_user_extra_info e on(u.`user_account`=e.`user_key`) where u.`bdp_user`= 0 ;",nativeQuery = true)
//	public List<Map<String,Object>> findAllSso();
}
