$(function () {
  $("#nickName_edit_btn").click(function () {
    let nickName = $("#nickName").val();
    if (nickName == null || nickName.trim() === "") {
      alert("새로운 닉네임을 입력하세요.");
      return;
    } else {
      $(".modal").css("display", "block");
      $("body").css("overflow", "hidden"); // 모달이 열리면 스크롤을 막기
    }
  });

  $(".close").click(function () {
    $(".modal").css("display", "none");
    $("body").css("overflow", "auto"); // 모달이 닫히면 스크롤을 복구
  });

  $("#modal_btn").click(function () {
    let password = $("#modal_password").val();
    let nickName = $("#nickName").val()

    if (password == null || password.trim() === "") {
      alert("현재 비밀번호를 입력하세요.");
      return;
    }

    $.ajax({
      type: 'post',
      url: `/api/users/profile/nickName`,
      data: JSON.stringify({password: password, nickName: nickName}),
      contentType: "application/json",
      success: function (data) {
        if (!data) {
          alert("비밀번호가 일치하지 않습니다.");
          $("#modal_password").val("").focus();
        } else {
          alert("닉네임이 정상적으로 변경되었습니다.");
          $("#nickName").attr("readonly", true);
          $(".modal").css("display", "none");
          $("body").css("overflow", "auto"); // 모달이 닫히면 스크롤을 복구
        }
      },
      error: function (error) {
        alert("비밀번호 확인 중 오류가 발생했습니다.");
      }
    });
  });
});

$(function () {
  $("#profileImageEditBtn").click(function() {
    $("#imageModal").css("display", "block");
    $("body").css("overflow", "hidden"); // 모달이 열리면 스크롤을 막기

    $("#modalUploadBtn").click(function() {
      let fileInput = $("#modalProfileImageInput")[0];

      // 사용자가 이미지를 선택했다면
      if(fileInput.files && fileInput.files.length === 1) {
        let formData = new FormData();
        formData.append("multipartFile", fileInput.files[0]);

        $.ajax({
          type: "post",
          url: "/api/uploads",
          data: formData,
          processData: false, // 필수: jQuery가 데이터를 처리하지 않도록 설정
          contentType: false, // 필수: jQuery가 contentType을 설정하지 않도록 설정
          success: function (data)  {
            alert("프로필 이미지가 정상적으로 변경되었습니다.");
            location.href="/users/profile"
          },
          error: function(err) {
            alert("프로필 이미지 변경 중 오류가 발생했습니다.")
          }
        })
      } else {
        alert("이미지를 선택하세요.")
      }
    })
  })
})