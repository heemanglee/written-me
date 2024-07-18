// 회원가입 검증 페이지

// 회원가입을 하기 위해서는 두 플래그가 true이어야 한다.
let emailChecked = false
let passwordChecked = false

// 이메일 중복 검사
$(function () {
  $("#checkEmailBtn").click(function () {
    let input_email = $("#email").val(); // 이메일 입력 필드 선택 수정
    if (!input_email) {
      alert("이메일을 작성하세요.");
      return
    }

    $.ajax({
      type: 'GET',
      url: `/users/sign-up/check-email/${encodeURIComponent(input_email)}`, // URL 인코딩 추가
      success: function (data) {
        if (!data) {
          alert('사용 가능한 이메일입니다.');
          $('#email').attr("readonly", true).css("background-color",
              "lightgrey") // 입력창 비활성화
          $("#nickName").focus();
          emailChecked = true
        } else {
          alert('이미 사용 중인 이메일입니다.');
          $("#email").val("").focus();
          emailChecked = false
        }
      },
      error: function (error) {
        alert('오류가 발생했습니다. 다시 시도해주세요.');
      }
    });
  });
});

// 비밀번호
$(function () {
  $("#checkPasswordBtn").click(function () {
    let password = $("#password").val()
    let confirmPassword = $("#confirmPassword").val()
    if (!password || !confirmPassword) {
      alert("비밀번호를 입력하세요.")
      return
    }

    $.ajax({
      type: 'GET',
      url: `/users/sign-up/check-password?password=${encodeURIComponent(
          password)}&confirmPassword=${encodeURIComponent(confirmPassword)}`,
      success: function (data) {
        if (data) {
          alert('비밀번호가 일치합니다.')
          $("#password").attr("readonly", true).css("background-color",
              "lightgrey")
          $("#confirmPassword").attr("readonly", true).css("background-color",
              "lightgrey")
          passwordChecked = true
        } else {
          alert("비밀번호가 일치하지 않습니다.")
          $("#password").val("").focus()
          $("#confirmPassword").val("")
          passwordChecked = false
        }
      }, error: function (data) {
        console.log(error)
      }
    })
  })
})

// 회원가입 버튼
$(function () {
  $("#signupForm").click(function(event) {
    if(!emailChecked) {
      alert("이메일 중복 검사를 완료하세요.")
      event.preventDefault() // 폼 제출 방지
    }
    else if(!passwordChecked) {
      alert("비밀번호 검사를 완료하세요.")
      event.preventDefault()
    }
  })
})
