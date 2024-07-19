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
    let proflileImageName = $("#profileImageName").val()

    $.ajax({
      type: "delete",
      url: `/api/users/profile`,
      data: JSON.stringify({deleteImageName: proflileImageName}),
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
});
