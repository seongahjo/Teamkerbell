function more() {
    var par = "page=" + page;
    $.ajax({
        url: "../moreTimeline",
        data: par,
        dataType: 'json',
        async: true,
        type: 'GET',
        success: function (data) {
            page += 1;
            var append = '';
            $.each(data, function (index, temp) {
                if (temp.contentid == 1) {
                    append += ' <div class="box-body chat"><div class=“item”><img src=‘../img/default.jpg alt=“user image” class=“online”><p class=“message”><small class=“text-used pull-right”><i class=“fa fa-clock”></i><span class="prettydate">' + $.datepicker.formatDate('yy-mm-dd', new Date(temp.date)) + '</span></small><a href=“../chat/' + temp.project.projectidx + '”>' + temp.project.name + '</a>에 일정이 추가되었습니다</p></div></div>';
                }
                else if (temp.contentid == 2) {
                    append += ' <div class="box-body chat"><div class="item"><img src="../' + temp.actor.img + '" alt="user image" class="online"><p class="message"><small class="text-muted pull-right"><i class="fa fa-clock-o"></i><span class="prettydate">' + $.datepicker.formatDate('yy-mm-dd', new Date(temp.date)) + '</span></small>' + temp.actor.id + '님이 <a href="../chat/' + temp.project.projectidx + '">' + temp.project.name + '</a>에 파일 업로드를 하셨습니다</p><div class="attachment"><h4>파일:</h4> <p class="filename">' + temp.filename + ' </p>  <div class="pull-right"> <a href="../' + temp.fileurl + '"><button type="button" class="btn btn-primary btn-sm btn-flat">Open</button></a></div></div> </div></div>';
                }
            });
            $("#timelne-box").append(append);
            $(".prettydate").prettydate();

        },
        error: function () {
            $("#error-message").fadeIn(600, function () {
                ($("#error-message")).fadeOut(800);
            });
        }
    })
}