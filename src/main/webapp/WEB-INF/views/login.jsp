<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>EchoDiary | Login</title>

    <style>
        :root {
            --teal: #2f8f8b;
            --teal-soft: #e6f4f3;
            --orange: #f28c38;
            --cream: #fff8ee;
            --text-main: #2b2b2b;
            --text-sub: #6b7280;
            --border: #e6e6e6;
        }

        * {
            box-sizing: border-box;
            font-family: "Pretendard", "Apple SD Gothic Neo", sans-serif;
        }

        body {
            margin: 0;
            height: 100vh;
            background: linear-gradient(135deg, var(--teal-soft), var(--cream));
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .login-wrapper {
            width: 380px;
            background: #ffffff;
            border-radius: 18px;
            padding: 36px 32px;
            box-shadow: 0 12px 30px rgba(0,0,0,0.08);
        }

        .logo {
            text-align: center;
            font-size: 26px;
            font-weight: 700;
            color: var(--teal);
            margin-bottom: 8px;
        }

        .subtitle {
            text-align: center;
            font-size: 14px;
            color: var(--text-sub);
            margin-bottom: 28px;
        }

        .subtitle span {
            color: var(--orange);
            font-weight: 600;
        }

        .login-form {
            display: flex;
            flex-direction: column;
            gap: 18px;
        }

        .field label {
            font-size: 13px;
            color: var(--text-sub);
            margin-bottom: 6px;
            display: block;
        }

        .field input {
            width: 100%;
            padding: 12px 14px;
            border-radius: 12px;
            border: 1px solid var(--border);
            font-size: 14px;
        }

        .field input:focus {
            outline: none;
            border-color: var(--teal);
            background: var(--teal-soft);
        }

        .login-btn {
            margin-top: 10px;
            padding: 14px;
            border-radius: 24px;
            border: none;
            background: var(--orange);
            color: white;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
        }

        .login-btn:hover {
            box-shadow: 0 0 0 6px rgba(242,140,56,0.25);
        }

        .error-msg {
            margin-top: 14px;
            text-align: center;
            color: #d9534f;
            font-size: 13px;
        }
    </style>
</head>

<body>

<div class="login-wrapper">

    <div class="logo">EchoDiary</div>
    <div class="subtitle">
        하루를 기록하고, <span>Echo</span>를 들어보세요
    </div>

    <!-- 🔐 Spring Security 기본 로그인 엔드포인트 -->
    <form class="login-form" method="post" action="/login">

        <!-- username = email -->
        <div class="field">
            <label>이메일</label>
            <input type="email" name="username" placeholder="email@echodiary.com" required>
        </div>

        <div class="field">
            <label>비밀번호</label>
            <input type="password" name="password" required>
        </div>

        <button type="submit" class="login-btn">로그인</button>
    </form>

    <!-- 로그인 실패 메시지 -->
    <c:if test="${param.error != null}">
        <div class="error-msg">
            이메일 또는 비밀번호가 올바르지 않습니다.
        </div>
    </c:if>

</div>

</body>
</html>
