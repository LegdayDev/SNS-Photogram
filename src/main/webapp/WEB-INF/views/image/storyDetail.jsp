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
                <div class="sl__item__img" onclick="popup('.modal-image')">
                    <form id="userImageForm">
                        <input type="file" name="userImageFile" style="display: none;"
                               id="userImageInput"/>
                    </form>
                    <img src="/upload/${image.postImageUrl}" id="userImage"/>
                </div>
                <div class="sl__item__contents">
                    <c:choose>
                        <c:when test="${principal.user.id==image.user.id}">
                            <div class="sl__item__contents__content">
                                <input type="text" id="userCaption" value="${image.caption}" size="100">
                                <button class="cta blue"
                                        onclick="userStoryUpdate(${image.id},${image.user.id},${principal.user.id})">
                                    수정하기
                                </button>
                                <button class="cta red" onclick="userStoryDelete(${image.id},${principal.user.id})">
                                    삭제하기
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

<!--스토리 이미지 바꾸기 모달 Start-->
<div class="modal-image" onclick="modalImage()">
    <div class="modal">
        <p>사진 변경</p>
        <button onclick="imageUpdate(${image.id},${image.user.id}, ${principal.user.id})">사진 업로드</button>
        <button onclick="closePopup('.modal-image')">취소</button>
    </div>
</div>
<!--스토리 이미지 바꾸기 모달 End-->

<script src="/js/detail.js"></script>
</body>
</html>