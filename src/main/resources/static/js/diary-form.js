$(function () {
  $("#diary_submit").click(function(event) {
    let isClicked = $("input:radio[name='feel']").is(":checked")
    let content = $("#content").val()
    if(!isClicked) {
      alert("기분을 선택하세요.")
      event.preventDefault()
    }
    else if(content == null) {
      alert("일기를 작성하세요.")
      event.preventDefault()
    }
  })
})