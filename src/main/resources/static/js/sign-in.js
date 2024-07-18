// 로그인 검증 페이지

// 이메일, 패스워드가 입력되었는지 검증
$(function () {
  $("#sign_in_btn").click(function (event) {
    let email = $("#email").val()
    let password = $("#password").val()
    if (!email) {
      alert("아이디를 입력하세요.")
      event.preventDefault()
    } else if (!password) {
      alert("비밀번호를 입력하세요")
      event.preventDefault()
    }
  })
})