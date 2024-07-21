// 모달 닫기 버튼
document.getElementsByClassName('closeInviteModal')[0].addEventListener('click', function() {
  document.getElementById('inviteModal').style.display = 'none';
});

// 바깥 클릭으로 모달 닫기
window.addEventListener('click', function(event) {
  if (event.target == document.getElementById('inviteModal')) {
    document.getElementById('inviteModal').style.display = 'none';
  }
});

// 초대 링크 복사 버튼
$(function ()  {
  $("#copyBtn").click(function() {
    var inviteLink = $("#create-inviteLink").val()
    navigator.clipboard.writeText(inviteLink).then(() => {
      alert("초대코드가 복사되었습니다.")
    })
  })
})