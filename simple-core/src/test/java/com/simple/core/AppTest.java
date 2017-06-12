package com.simple.core;

import java.io.IOException;

import com.cheuks.bin.orginal.beetl.generate.CreateFile;
import com.simple.core.entity.Appointment;
import com.simple.core.entity.Article;
import com.simple.core.entity.Authority;
import com.simple.core.entity.AuthorityGroup;
import com.simple.core.entity.KeyValueExtend;
import com.simple.core.entity.PersonnelInfo;
import com.simple.core.entity.Tenant;
import com.simple.core.entity.User;

import freemarker.template.TemplateException;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	public static void main(String[] args) throws IOException, TemplateException {
		CreateFile.create(Appointment.class, Long.class, true, false, false);
		CreateFile.create(Article.class, Long.class, true, false, false);
		CreateFile.create(Authority.class, Long.class, true, false, false);
		CreateFile.create(AuthorityGroup.class, Long.class, true, false, false);
		CreateFile.create(KeyValueExtend.class, Long.class, true, false, false);
		CreateFile.create(PersonnelInfo.class, Long.class, true, false, false);
		CreateFile.create(Tenant.class, Long.class, true, false, false);
		CreateFile.create(User.class, Long.class, true, false, false);
	}
}
