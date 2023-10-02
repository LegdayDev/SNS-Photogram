<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>

<main class="main">
    <section class="container">
        <article class="story-list" id="story">

            <div class="story-list__item">
                <div class="sl__item__header">
                    <div>
                        <img class="profile-image" src="/upload/${image.user.profileImageUrl}"
                             tonerror="this.src='/images/person.jpeg'"/>
                    </div>
                    <div>${image.user.username}</div>
                </div>
                <div class="sl__item__img">
                    <img src="/upload/${image.postImageUrl}"/>
                </div>
                <div class="sl__item__contents">
                    <c:choose>
                        <c:when test="${principal.user.id==image.user.id}">
                            <div class="sl__item__contents__content">
                                <input type="text" value="${image.caption}" size="100">
                                <button class="cta blue">
                                    수정하기
                                </button>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="sl__item__contents__content">
                                <p>${image.caption}</p>
                            </div>
                        </c:otherwise>

                    </c:choose>

                </div>
            </div>
        </article>
    </section>
</main>
<script src="/js/story.js"></script>
</body>
</html>