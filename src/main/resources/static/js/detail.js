/**
 * 1) 이미지삭제(imageId, userId)
    - imageId 를 통해 이미지 삭제하고 외래키로 연결된 엔티티 같이 삭제
    - userId 로 user 프로필 페이지로 이동
 * 2) 이미지 업데이트(imageId, pageUserId, principalId)
    - FormData 를 이용하여 수정할 이미지와 caption(내용)을 저장
    - FormData 로 image 엔티티 수정 후 이미지 상세 화면으로 이동
 * 3) 이미지 업로드 후 페이지로드 없이 반영
    - pageUserId(이미지 작성 유저) 와 principalId(로그인한 유저) 를 비교하여 다르면 수정할 수없게 막는다.
    - 맞다면 모달창을 띄어서 이미지 업로드를 구현
 * 4) 사진 업로드, 취소 모달창
 * 5) 사용자 정보 메뉴 열기
 * 6) 사용자 정보 메뉴 닫기
 */


// 1) 이미지삭제(imageId,userId)
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

// 2) 이미지 업데이트(imageId, pageUserId, principalId)
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

// 3) 이미지 업로드 후 페이지 로드 없이 반영
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

// 4) 사진 업로드, 취소 모달창
function modalImage() {
    $(".modal-image").css("display", "none");
}

// 5) 사용자 정보 메뉴 열기
function popup(obj) {
    $(obj).css("display", "flex");
}
// 6) 사용자 정보 메뉴 닫기
function closePopup(obj) {
    $(obj).css("display", "none");
}
