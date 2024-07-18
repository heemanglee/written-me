$(function () {
  $(".nickName").click(function () {
    let index = parseInt($(this).attr("data-index"))
    // let diary = diaryList[index]

    $.ajax({
      type: "get",
      url: `/diarys/${index + 1}`,
      contentType: "application/json",
      success: function (data) {
        $(".modal").css("display", "block");
        $("body").css("overflow", "hidden"); // 모달이 열리면 스크롤을 막기
        $("#modal_content").val(data.content)

        var emoji = null
        switch (data.feel) {
          case "HAPPY":
            emoji = "😊"
            break
          case "GOOD":
            emoji = "🙂"
            break
          case "NOT_BAD":
            emoji = "😐"
            break
          case "BAD":
            emoji = "😞"
            break
          case "ANGRY":
            emoji = "😠"
        }
        $("#modal_feel").text(`기분 : ${emoji}`)

      },
      error: function (err) {
        alert("서버에 장애가 발생하였습니다.")
        return
      }
    })
  });

  $(".close").click(function () {
    $(".modal").css("display", "none");
    $("body").css("overflow", "auto"); // 모달이 닫히면 스크롤을 복구
  });
});
