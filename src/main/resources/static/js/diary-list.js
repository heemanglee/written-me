$(function () {
  $(".nickName").click(function () {
    let index = parseInt($(this).attr("data-index"))
    // let diary = diaryList[index]

    $.ajax({
      type: "get",
      url: `/diarys/${index}`,
      contentType: "application/json",
      success: function (data) {
        $(".modal").css("display", "block");
        $("body").css("overflow", "hidden"); // 모달이 열리면 스크롤을 막기
        $("#modal_content").val(data.content)
        $("#ai_response_content").val(data.aiResponse)

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

$(document).ready(function () {
  const urlParams = new URLSearchParams(window.location.search);
  const diaryId = urlParams.get('diaryId');
  if (diaryId) {
    // diaryId에 해당하는 일기의 내용을 서버에서 가져와 모달을 띄운다.
    $.ajax({
      type: "get",
      url: `/diarys/${diaryId}`,
      contentType: "application/json",
      success: function (data) {
        $(".modal").css("display", "block")
        $("body").css("overflow", "hidden") // 모달이 열리면 스크롤을 막기
        $("#modal_content").val(data.content)
        $("#ai_response_content").val(data.aiResponse)

        var emoji = null;
        switch (data.feel) {
          case "HAPPY":
            emoji = "😊";
            break;
          case "GOOD":
            emoji = "🙂";
            break;
          case "NOT_BAD":
            emoji = "😐";
            break;
          case "BAD":
            emoji = "😞";
            break;
          case "ANGRY":
            emoji = "😠";
        }
        $("#modal_feel").text(`기분 : ${emoji}`);
      },
      error: function (err) {
        alert("서버에 장애가 발생하였습니다.");
        return;
      }
    });
  }

  $(".close").click(function () {
    $(".modal").css("display", "none");
    $("body").css("overflow", "auto"); // 모달이 닫히면 스크롤을 복구
  });
});

$(function () {
  $(".like-btn").click(function () {
    let index = parseInt($(this).attr("data-index"))
    alert(index)
    $.ajax({
      type: "PATCH",
      url: `/diarys/${index}/like`,
      success: function (data) {
        location.href="/diarys"
      },
      error: function(err) {
        alert("서버가 장애가 발생하였습니다.")
      }
    })
  })
})