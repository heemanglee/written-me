function getEmoji(feel) {
  let emoji = "";
  switch (feel) {
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
  return emoji
}

$(function () {
  $(".content-summary").click(function () {
    let index = parseInt($(this).attr("data-index"))

    $.ajax({
      type: "get",
      url: `/api/diarys/${index}`,
      contentType: "application/json",
      success: function (data) {
        $(".modal").css("display", "block");
        $("body").css("overflow", "hidden"); // 모달이 열리면 스크롤을 막기
        $("#modal_content").val(data.content)
        $("#ai_response_content").val(data.aiResponse)

        var emoji = getEmoji(data.feel)

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

  $("#search-btn").click(function () {
    let selectedFeelings = [];

    // 모든 체크박스를 가져옴
    let checkboxes = document.querySelectorAll(
        'input[name="search-feel"]:checked');

    // 체크된 체크박스의 값을 리스트에 추가
    checkboxes.forEach((checkbox) => {
      selectedFeelings.push(checkbox.value);
    });

    let feelingsParam = selectedFeelings.join(',');

    let emojis = document.querySelector('.dropdown-toggle').textContent

    $.ajax({
      url: `/api/diarys?feels=${feelingsParam}`,
      type: "get",
      success: function (data) {
        $("#toggle-btn").html(emojis)
        location.href = "/diarys?feels=" + feelingsParam;

        // 기존 콘텐츠를 지움
        $(".results").empty();

        // 필터링된 데이터로 새로운 HTML을 구성하여 추가
        data.forEach(diary => {
          let likeStatus = diary.likeStatus ? '❤️' : '🩶';
          let diaryHtml = `
                        <div class="result-item" data-index="${diary.diaryId}">
                            <div class="profile">
                                <img src="/path/to/profile/image" alt="Profile Image">
                            </div>
                            <div class="details nickName" data-index="${diary.diaryId}">
                                <span>${diary.nickName}</span>
                            </div>
                            <div class="time">
                                <p>
                                    <span class="diary-date">${diary.diaryDate}</span>
                                </p>
                            </div>
                            <div class="mood mood-${diary.feel}">
                                <span>${diary.feel}</span>
                            </div>
                            <div class="like">
                                <button class="like-btn" type="button" data-index="${diary.diaryId}">
                                    ${likeStatus}
                                </button>
                            </div>
                        </div>
                    `;
          $(".results").append(diaryHtml);
        });
      },
      error: function (err) {
        alert("필터링에 실패하였습니다.");
      }
    });
  });
});

$(document).ready(function () {
  const urlParams = new URLSearchParams(window.location.search);
  const diaryId = urlParams.get('diaryId');

  const feelings = urlParams.get('feels');
  if (feelings) {
    let emojis = "";
    const feelingsArray = feelings.split(',');
    feelingsArray.forEach((feel, index) => {
      switch (feel) {
        case "HAPPY":
          emojis += "😊행복";
          break;
        case "GOOD":
          emojis += "🙂좋음";
          break;
        case "NOT_BAD":
          emojis += "😐보통";
          break;
        case "BAD":
          emojis += "😞나쁨";
          break;
        case "ANGRY":
          emojis += "😠화남";
          break;
      }
      if (index < feelingsArray.length - 1) {
        emojis += ", ";
      }
    });
    $("#toggle-btn").text(emojis.trim());
  }

  if (diaryId) {
    // diaryId에 해당하는 일기의 내용을 서버에서 가져와 모달을 띄운다.
    $.ajax({
      type: "get",
      url: `/api/diarys/${diaryId}`,
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

    $.ajax({
      type: "PATCH",
      url: `/api/diarys/${index}/like`,
      success: function (data) {
        let currentUrlParams = new URLSearchParams(window.location.search);
        let feelingsParam = currentUrlParams.get('feels');
        if (feelingsParam == null) {
          location.href = "/diarys"
        } else {
          location.href = `/diarys?feels=${feelingsParam}`
        }
      },
      error: function (err) {
        alert("서버가 장애가 발생하였습니다.")
      }
    })
  })
})

// 드롭다운
function toggleDropdown() {
  const dropdownMenu = document.querySelector('.dropdown-menu');
  dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
}

function updateLabel() {
  const checkboxes = document.querySelectorAll('.dropdown-item input');
  const labels = Array.from(checkboxes).filter(
      checkbox => checkbox.checked).map(
      checkbox => checkbox.parentNode.textContent.trim());
  document.querySelector('.dropdown-toggle').textContent = labels.join(', ');
}

function applySelection() {
  toggleDropdown();
}

window.onclick = function (event) {
  if (!event.target.matches('.dropdown-toggle') && !event.target.matches('.apply-btn') && !event.target.matches('.dropdown-item input')) {
    const dropdownMenus = document.getElementsByClassName('dropdown-menu');
    for (let i = 0; i < dropdownMenus.length; i++) {
      let openDropdown = dropdownMenus[i];
      if (openDropdown.style.display === 'block') {
        openDropdown.style.display = 'none';
      }
    }
  }
}

// 날짜 포매팅 함수, LocalDateTime -> "yyyy년 MM월 dd일"
function formatDate(dateString) {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = date.getMonth() + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
  const day = date.getDate();
  return `${year}년 ${month}월 ${day}일`;
}

// 페이지 로드 시 실행되는 함수
document.addEventListener("DOMContentLoaded", function() {
  // 모든 날짜 요소 선택
  const dateElements = document.querySelectorAll('.diary-date');

  // 각 날짜 요소에 대해 포매팅 수행
  dateElements.forEach(function(element) {
    const dateString = element.textContent;
    const formattedDate = formatDate(dateString);
    element.textContent = formattedDate;
  });
});

$(function() {
  $("#couple_submit_btn").click(function() {
    $.ajax({
      type: "post",
      url: "/api/couples",
      success: function(data) {
        var connectionToken = data.connectionToken
        $("#create-inviteLink").val(connectionToken)
        document.getElementById('inviteModal').style.display = 'block';
      },
      error: function(err) {
        alert("이미 커플이 등록되어 있습니다!")
      }
    })
  })
})

$(function() {
  $("#enrollBtn").click(function() {
    const token = document.getElementById('enroll-inviteLink').value;
    $.ajax({
      type: "get",
      url: `/api/couples?token=${token}`,
      success: function(data) {
        const senderId = data.senderId

        $.ajax({
          type: "patch",
          url: "/api/couples",
          data: JSON.stringify({senderId: senderId}),
          contentType: "application/json",
          success: function(data) {
            alert("커플 등록이 완료되었습니다!")
            location.href="/diarys"
          },
          error: function(err) {
            alert("커플 등록에 실패하였습니다.")
          }
        })
      }
    })
  })
})