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

<script>
    // 실제 업데이트 정보(image, caption) 서버로 전송
    function userStoryUpdate(imageId, pageUserId, principalId) {
        let formData = new FormData($("#userImageForm")[0]);
        formData.append("caption",$("#userCaption").val());

        $.ajax({ // 여기부터 수정
            type: "put",
            url: `/api/image/${imageId}`,
            data: formData,
            enctype: "multipart/form-data",
            contentType: false, // 필수 : x-www-form-urlencoded 로 파싱되는 것을 방지
            processData: false, // 필수 : contentType 이 false 일 경우에 QueryString 으로 자동 설정됨. 해제
            dataType: "json"
        }).done(res => {
            console.log(res);
            alert("이미지 수정 완료 !!");
            location.href=`/image/detail/${imageId}`;
        }).fail(error => {
            console.log("오류", error);
        });


    }

    // 수정된 이미지 반영
    function imageUpdate(imageId, pageUserId, principalId) {
        if (pageUserId != principalId) {
            alert("사진을 수정할 수 없는 유저입니다.");
            return; //메서드 종료
        }

        $("#userImageInput").click();

        $("#userImageInput").on("change", (e) => {
            let f = e.target.files[0];

            if (!f.type.match("image.*")) {
                alert("이미지를 등록해야 합니다.");
                return;
            }

            let reader = new FileReader();
            reader.onload = (e) => {
                $("#userImage").attr("src", e.target.result);
            }
            reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
        });
    }

    // (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
    function modalImage() {
        $(".modal-image").css("display", "none");
    }

    // (4) 사용자 정보 메뉴 열기 닫기
    function popup(obj) {
        $(obj).css("display", "flex");
    }

    function closePopup(obj) {
        $(obj).css("display", "none");
    }

</script>
</body>
</html>