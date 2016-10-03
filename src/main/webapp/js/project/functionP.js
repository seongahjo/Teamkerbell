/**
 * Created by seongahjo on 2016. 7. 24..
 */

var invited; // 초대된 사람의 아이디
var scheduleStart; // 시작시간
var scheduleEnd; // 종료시간
var modalName; // 파일 버전 모달
var modalPage; // 파일 버전 페이지

function fileDialog() {
    document.getElementById("file").click();
}


function search() {
    var par = {
        userId: $("#inviteForm #inviteId").val(),
        projectIdx: projectIdx
    };
    var querystring = $.param(par);
    $.ajax({
        url: "../inviteUser",
        type: 'POST',
        dataType: 'json',
        data: querystring,
        success: function (data) {
            invited = data.id;
            $("#user").html('<div class="box box-primary" style="width:70%; margin-left:15%; margin-top:5%"> <div class="box-body box-profile"> <img class="profile-user-img img-responsive img-circle" src="' + "../" + data.img + '"alt="User profile picture"> <h3 class="profile-username text-center">' +data.id+ '</h3> <p class="text-muted text-center">' + data.name + '</p><a href="#" class="btn btn-primary btn-block" onclick="invite()"><b>Invite</b></a></div> </div>');
        },
        error: function () {
            $("#InviteUser").find("#error-message").fadeIn(600, function () {
                $("#InviteUser").find("#error-message").fadeOut(800);
            });
            console.log('error');
            //$("#user").html('<div style="text-align:center;"> <img src="../img/cry.png"  width="50%" height="200px"> <p> User Info doesnt exist</p> </div>');
        }
    });
}



function invite() {
    var par = "userId=" + invited + "&projectIdx="+projectIdx;
    $.ajax({
        url: "../inviteUser",
        data: par,
        dataType: 'text',
        async: true,
        type: 'GET',
        success: function (data) {
            socket.emit('invite', {userIdx: data});
            console.log(data + "Invite");
            $("#InviteUser").modal('hide');
        }
    });
}

function makeSchedule() {
    var param = {
        projectIdx: projectIdx,
        startdate: scheduleStart,
        enddate: scheduleEnd,
        state : 0,
        content: $("#scheduleform").find($("#goal")).val(),
        place : $("#scheduleform").find($("#place")).val()
    };
    var querystring = $.param(param); // toJSON

    $.ajax({
        url: "../schedule",
        type: 'POST',
        data: querystring,
        processData: false,
        success: function () {
            $("#scheduleform").find("#success-message").fadeIn(1000, function () {
                $("#scheduleModal").modal('hide');
            })
        },
        error: function () {
            console.log('bb');
            $("#scheduleform").find("#error-message").fadeIn(600, function () {
                $("#scheduleModal").find($("#error-message")).fadeOut(800);
            });
        }
    });

}


function makeTodolist() {
    var param = {
        projectIdx: projectIdx,
        userId: $("#todoselect").children("option:selected").val(),
        startdate: scheduleStart,
        enddate: scheduleEnd,
        content: $("#todocontent").val()
    };
    var querystring = $.param(param);

    $.ajax({
        url: "../todolist",
        type: 'POST',
        data: querystring,
        processData: false,
        success: function () {
            $("#todoform").find($("#success-message")).fadeIn(1000, function () {
                $("#todoMadal").modal('hide');
            })
        },
        error: function () {
            $("#todoform").find($("#error-message")).fadeIn(600, function () {
                $("#todoform").find($("#error-message")).fadeOut(800);
            });
        }
    });

}

function endsWith(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

function upload() {
    var form = $("#uploadForm")[0];
    var formData = new FormData(form);
    $.ajax({
        url: "../file",
        type: "POST",
        dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            socket.emit("file", {
                msg: data,
                user: user.name,
                date: new Date().toString('HH:mm'),
                type: data.type
            });
        }
    });
}


function searchTable() {
    var searchVal = $("#tokenfield-typeahead").val();
    searchVal = searchVal.replace(/,/gi, "");
    table.search(searchVal).draw();
}

function openFile(name, page) {
    modalName = name;
    modalPage = page;
    openFileFlag(0);
}

function openFileFlag(flag) {
    var val = "";
    if (flag === 1) { // left
        if (modalPage != 0) {
            modalPage -= 1;
        }
        else
            return;
    }
    if (flag === 2) //right
        modalPage += 1;
    var param = "name=" + modalName + "&page=" + modalPage;
    $.ajax({
        url: "../file/"+projectIdx+"/name",
        type: 'GET',
        dataType: 'json',
        data: param,
        success: function (data) {
            if (data == null && flag == 2)
                modalPage -= 1;
            $.each(data, function (index, temp) {
                $("#downloadModal .modal-title").text(modalName);
                val += "<tr>" +
                    "<td>" + temp.count + "</td>" +
                    "<td>" + temp.file + "</td>" +
                    "<td>" + temp.uploader + "</td>" +
                    "<td>" + temp.date + "</td>";

            });
            $("#filetable").html(val);
        },
        error: function () {

        }
    })
}
