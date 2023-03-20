package com.todaysfailbe.common.utils;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.todaysfailbe.common.exception.NotFoundUserInfoInSessionException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionUtil {

	private static final String PREFIX_USER_ID = "LOGIN_USER_ID";
	private final HttpSession httpSession;

	public void saveUserIdInSession(Long id) {
		httpSession.setAttribute(PREFIX_USER_ID, id);
	}

	public Long findUserIdBySession() {
		Object userId = httpSession.getAttribute(PREFIX_USER_ID);
		if (userId == null) {
			log.info("[SessionUtil.findUserIdBySession] 세션 조회 실패");
			httpSession.invalidate();
			throw new NotFoundUserInfoInSessionException("세션에 사용자 정보가 저장되어 있지 않습니다");
		}
		return Long.valueOf(userId.toString());
	}

	public void invalidateSession() {
		httpSession.invalidate();
	}
}
