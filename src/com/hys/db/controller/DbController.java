/**************************************************************************************** 
 Copyright © 2014-2018 Changsha ZTEsoft Corporation. All rights reserved. Reproduction or <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hys.db.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hys.common.api.base.BaseApi;
import com.hys.common.utils.Loggers;
import com.hys.db.model.DbUser;
import com.hys.db.service.DbUserService;

/**
 * Description: <br>
 * 
 * @author li.yangqi<br>
 * @version 8.0<br>
 * @taskId <br>
 * @CreateDate 2017年7月11日 <br>
 * @since V8<br>
 * @see com.hys.db <br>
 */
@RestController
@RequestMapping("/api/db")
public class DbController implements BaseApi {

    @Autowired
    private DbUserService userService;

    private Logger logger = Loggers.getLogger(DbController.class);

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public DbUser get() {
        DbUser user = userService.getUser();
        userService.getAllUser();
        DbUser user2 = userService.getUser("caffeine");
        logger.debug("{} {} {}", user2.getUserName(), user2.getPwd(), user2);

        DbUser user3 = userService.getUser("caffeine");
        logger.debug("{} {} {}", user3.getUserName(), user3.getPwd(), user3);

        return user;
    }
}
