// 이미지삭제(imageId,userId)
function userStoryDelete(imageId,userId){
    if(confirm("삭제 하시겠습니까 ?")){
        $.ajax({
            type: "delete",
            url : `/api/image/${imageId}`,
            data : imageId,
            dataType: "json"
        }).done(res=>{
            console.log("성공",res);
            alert("이미지 삭제 성공 !");
            location.href=`/user/${userId}`;
        }).fail(error=>{
            console.log("실패",error);
        });
    }

}

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
