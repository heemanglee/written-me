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
      url: `/users/profile/nickName`,
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