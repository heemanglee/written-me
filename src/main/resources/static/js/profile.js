$(function () {
  $("#update_btn").click(function (event) {
    event.preventDefault();

    let currentPassword = $("#current_password").val();
    let newPassword = $("#new_password").val();
    let confirmPassword = $("#confirm_password").val();

    if (currentPassword == null || currentPassword.trim() === "") {
      alert("현재 비밀번호를 입력하세요.");
      $("#current_password").focus();
      return;
    } else if (newPassword == null || newPassword.trim() === "") {
      alert("새로운 비밀번호를 입력하세요.");
      $("#new_password").focus();
      return;
    } else if (confirmPassword == null || confirmPassword.trim() === "") {
      alert("2차 확인을 위한 비밀번호를 입력하세요.");
      $("#confirm_password").focus();
      return;
    }

    $.ajax({
      type: "post",
      url: `/api/users/profile/password`,
      data: JSON.stringify({password: currentPassword}),
      contentType: "application/json",
      success: function (data) {
        if (!data) {
          alert("현재 비밀번호가 일치하지 않습니다.");
        } else {
          if (newPassword === confirmPassword) {
            $.ajax({
              type: "patch",
              url: `/api/users/profile/password`,
              data: JSON.stringify({password: newPassword}),
              contentType: "application/json",
              success: function (data) {
                alert("비밀번호가 정상적으로 변경되었습니다.");
                window.location.href = "/";
              },
              error: function (error) {
                alert("비밀번호 변경 중 오류가 발생했습니다.");
              }
            });
          } else {
            alert("새 비밀번호와 2차 비밀번호가 일치하지 않습니다.");
            $("#confirm_password").focus();
          }
        }
      },
      error: function (error) {
        alert("비밀번호 확인 중 오류가 발생했습니다.");
      }
    });
  });

  $("#profileImageRemoveBtn").click(function () {
    // let profileImage = $("#profile_image").attr("src");
    let profileImageName = $("#profileImageName").val()

    $.ajax({
      type: "delete",
      url: `/api/users/profile`,
      data: JSON.stringify({deleteImageName: profileImageName}),
      contentType: "application/json",
      success: function(data) {
        alert("이미지 정상적으로 삭제되었습니다.")
        window.location.href= "/users/profile"
      },
      fail: function(err) {
        alert("이미지 삭제 중 오류가 발생했습니다.")
      }
    })
  })

  $("#profileImageEditBtn").click(function() {
    $(".image-modal").css("display", "block");
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

  $(".image-close").click(function () {
    $(".image-modal").css("display", "none");
    $("body").css("overflow", "auto"); // 모달이 닫히면 스크롤을 복구
  });
});
