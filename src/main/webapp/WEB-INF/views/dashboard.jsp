<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Teamkerbell</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="../css/wait1.css">
    <link rel="stylesheet" href="../css/jquery-jvectormap-1.2.2.css">


    <link rel="stylesheet" href="../css/wait2.css">

    <!-- daterange picker -->
    <link rel="stylesheet" href="../css/daterangepicker-bs3.css">
    <link rel="stylesheet" href="../css/bootstrap-timepicker.min.css">
    <link rel="stylesheet" href="../css/dataTables.bootstrap.css">

    <!-- fullcalendar-->
    <link rel="stylesheet" href="../css/fullcalendar.css">
    <link rel="stylesheet" href="../css/fullcalendar.print.css" media='print'>

    <!-- tag -->
    <link rel="stylesheet" href="../css/addtag.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <header class="main-header">

        <!-- Logo -->
        <a href="../dashboard/${user.id}" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>T</b>eam</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>Teamker</b>Bell</span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <!-- Navbar Right Menu -->

            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">

                <ul class="nav navbar-nav">


                    <li class="dash-icon">
                        <a class="dash-nav">
                            <i class="fa fa-picture-o"></i></a>
                    </li>


                    <li class="dash-icon">
                        <a class="dash-nav">
                            <i class="fa fa-edit"></i>
                        </a>
                    </li>
                    <!--
                                        <li class="dash-icon">
                                            <a class="dash-nav">
                                                <i class="fa fa-file-text-o"></i>
                                            </a>
                                        </li>
                                        <li class="dash-icon">
                                            <a class="dash-nav">
                                                <i class="fa fa-calendar-o"></i>
                                            </a>
                                        </li>-->
                    <!-- Notifications Menu -->
                    <li class="dropdown notifications-menu">
                        <!-- Menu toggle button -->
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o" id="alarm"></i>
                            <span class="label label-warning" id="alarm-size">${alarm.size()}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header" id="alarm-content">You have ${alarm.size()} notifications</li>
                            <li>
                                <ul class="menu" style="max-height:400px;overflow-y:auto" id="alarm-list">
                                    <c:forEach var="list" items="${alarm}">
                                        <li id="alarm-${list.alarmidx}">
                                            <a href="#">
                                                <i class="fa fa-users text-aqua"></i><strong>${list.actor.id}</strong>
                                                has invited you
                                                to <strong>${list.project.name}</strong>
                                                <div style="float:right;">
                                                    <button type="button" class="btn btn-primary btn-xs"
                                                            onclick="accept('${list.alarmidx}')">Ok
                                                    </button>
                                                    <button type="button" class="btn btn-default btn-xs"
                                                            onclick="decline('${list.alarmidx}')">Cancel
                                                    </button>
                                                </div>
                                            </a>
                                        </li>
                                    </c:forEach>

                                </ul>
                            </li>
                            <li class="footer"> <!--<a href="#">View all</a></li>-->
                        </ul>
                    </li>
                    <!-- User Account Menu -->
                    <li class="dropdown user user-menu">
                        <!-- Menu Toggle Button -->
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <!-- The user image in the navbar-->
                            <img src="../${user.img}" class="user-image" alt="" style="width:25px;height:25px">
                            <!-- hidden-xs hides the username on small devices so only the image appears. -->
                            <span class="hidden-xs">${user.id}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- The user image in the menu -->
                            <li class="user-header">
                                <img src="../${user.img}" class="img-circle" alt="User Image"
                                     style="width:90px;height:90px">
                                <p>
                                    ${user.id}
                                </p>
                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-6 text-center" style="border-right:1px solid;">
                                        <a href="../projectmanager"><i class="fa fa-pencil-square-o"></i> Project
                                            Edit</a>
                                    </div>
                                    <div class="col-xs-6 text-center">
                                        <a href=../userInfo/${user.id}><i class="fa fa-info-circle"></i> MyInfo
                                            Edit</a>
                                    </div>

                                </div>
                                <!-- /.row -->
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <!--
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">Profile</a>
                                </div>
                                -->
                                <div class="pull-right">
                                    <a href="../logout" class="btn btn-default btn-flat">Sign out</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <!-- Control Sidebar Toggle Button -->

                </ul>
            </div>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">

        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

            <!-- Sidebar user panel (optional) -->
            <div class="user-panel">
                <div class="pull-left image">
                    <img src="../${user.img}" class="img-circle" alt="User Image" style="width:100px;height:100px">
                </div>
                <div class="pull-left info dash-user">
                    <p>${user.id}</p>
                    <!-- Status -->
                    <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>

            <!-- Sidebar Menu -->
            <ul class="sidebar-menu">
                <li class="header"> Main Menu</li>
                <!-- Optionally, you can add icons to the links -->

                <li class="treeview">
                    <a href="#"><i class="fa fa-user"></i><span> My Profile</span></a>
                    <ul class="treeview-menu">
                        <li class="active"><a href=../userInfo/${user.id}><i class="fa fa-key"></i> Change user Info</a>
                        </li>

                    </ul>
                </li>

                <li class="treeview">
                    <a href="#"><i class="fa fa-users"></i><span> Project Rooms</span></a>
                    <ul class="treeview-menu">
                        <li>
                            <c:forEach var="list" items="${projects}"> <!-- 컨트롤러에서 넘겨받은 프로젝트를 list에 삽입 -->
                                <a href="../chat/${list.projectidx}"
                                   class="side-nav-button">name: ${list.name}</a>
                            </c:forEach>
                            <a href="../projectmanager"> <i class="fa fa-cogs"></i><span>Edit</span></a>
                        </li>
                    </ul>
                </li>

            </ul>
            <!-- /.sidebar-menu -->
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Dashboard
                <!-- <small>Optional description</small>-->
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">


            <div class="row">
                <div class="col-md-8">
                    <div class="box box-success time-line">
                        <div class="box-header">
                            <i class="fa fa-comments-o"></i>

                            <h3 class="box-title">타임라인</h3>

                            <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
                                <div class="btn-group" data-toggle="btn-toggle">

                                </div>
                            </div> <!-- status -->
                        </div> <!-- header -->
                        <c:if test="${empty timeline}">
                            <img class="notime" src="../img/Notime.png">
                        </c:if>
                        <c:forEach var="list" items="${timeline}">
                            <div class="box-body chat" id="chat-box">
                                <!--타임라인 시작 -->

                                <c:choose>
                                    <c:when test="${list.contentid=='1'}">
                                        <!-- 일정 -->
                                        <div class="item">
                                            <img src="../img/default.jpg" alt="user image" class="online">

                                            <p class="message">
                                                <small class="text-muted pull-right"><i
                                                        class="fa fa-clock-o"></i><span
                                                        prettydate> ${list.date}</span></small>
                                                <a href="../chat/${list.project.projectidx}"> ${list.project.name}</a>
                                                에서 일정이 추가되었습니다
                                            </p>
                                        </div>
                                        <!-- /.item -->
                                    </c:when>
                                    <c:when test="${list.contentid=='2'}">
                                        <!-- 파일 -->
                                        <div class="item">
                                            <img src="../${list.actor.img}" alt="user image" class="online">

                                            <p class="message">

                                                <small class="text-muted pull-right"><i class="fa fa-clock-o"></i><span
                                                        prettydate> ${list.date}</span></small>
                                                    ${list.actor.id}님이 <a
                                                    href="../chat/${list.project.projectidx}">${list.project.name}</a>에
                                                파일
                                                업로드를 하셨습니다
                                            </p>
                                            <div class="attachment">
                                                <h4>파일:</h4>

                                                <p class="filename">
                                                        ${list.filename}
                                                    <!-- download?date="+date+"&projectidx="+${userId}+"&filename="+$(this).text(); -->
                                                </p>

                                                <div class="pull-right">
                                                    <a href="../${list.fileurl}">
                                                        <button type="button" class="btn btn-primary btn-sm btn-flat">
                                                            Open
                                                        </button>
                                                    </a>
                                                </div>
                                            </div>
                                            <!-- /.attachment -->
                                        </div>
                                        <!-- 파일 -->

                                    </c:when>
                                </c:choose>

                            </div>
                            <!-- /.chat -->
                        </c:forEach>

                    </div>
                    <button class="btn btn-primary btn-sm btn-flat" onclick="moreView()">more</button>
                </div>
                <!-- TOdo -->
                <section class="col-md-4 connectedSortable">
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="ion ion-clipboard"></i>

                            <h3 class="box-title">할 일</h3>

                            <div class="box-tools pull-right vec">
                                <ul class="pagination pagination-sm inline vec">

                                </ul>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <ul class="todo-list">
                                <c:forEach var="list" items="${todolist}">
                                <c:choose>
                                <c:when test="${list.ok=='0'}">
                                <li class="done">
                                    </c:when>
                                    <c:otherwise>
                                <li>
                                    </c:otherwise>
                                    </c:choose>
                                    <span class="handle">
                        <i class="fa fa-ellipsis-v"></i>
                        <i class="fa fa-ellipsis-v"></i>
                      </span>

                                    <c:choose>
                                    <c:when test="${list.ok=='0'}">
                                    <input type="checkbox" class="cb" checked value="${list.todolistidx}">
                                    </c:when>
                                    <c:otherwise>
                                    <input type="checkbox" class="cb" value="${list.todolistidx}">
                                    </c:otherwise>
                                    </c:choose>
                                    <span> ${list.project.name}</span>
                                    <span class="text">${list.content}</span>

                                    <small class="label label-danger" prettydate><i
                                            class="fa fa-clock-o"></i>${list.enddate}</small>

                                    <div class="tools">
                                        <i class="fa fa-trash-o"></i>
                                    </div>

                                    </c:forEach>
                            </ul>
                        </div>
                    </div>


                    <div class="box">
                        <div class="box-header add-header">
                            <i class="fa fa-calendar-plus-o"></i>
                            <h3 class="box-title"> 스케쥴</h3>

                            <div class="box-tools">

                            </div>
                        </div>

                        <!-- /.box-header -->
                        <div id="calendar" class="box-body table-responsive no-padding">
                            <!--  <table class="table table-hover">
                                <tr>
                                    <th>Project</th>
                                    <th>Duration</th>
                                    <th>Register</th>
                                </tr>
                                <c:forEach var="list" items="${schedules}">
                                    <c:choose>
                                        <c:when test="${list.state=='0'}">
                                            <tr>
                                                <td>${list.project.name}</td>
                                                <td>${list.startdate} - ${list.enddate}</td>
                                                <td>
                                                    <a class="btn btn-primary btn-xs" data-toggle="modal"
                                                       data-target="#Rgmodal"
                                                       onclick="toregister('${list.scheduleidx}','${list.startdate}','${list.enddate}')">Register</a>


                                                </td>
                                            </tr>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </table>
                        </div>-->
                            <!-- /.box-body -->


                        </div>
                    </div>
                </section>


            </div>


        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <footer class="main-footer">
        <!-- To the right -->
        <div class="pull-right hidden-xs">
            Teamkerbell
        </div>

    </footer>

    <!-- Control Sidebar -->

    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<!--register modal -->
<div class="modal fade" id="Rgmodal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">Register Schedule</h4>
            </div>
            <form id="registerform">
                <div class="modal-body">

                    <div class="form-group">

                        <label>Sign the Date range:</label>

                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" class="form-control pull-right" id="reservation">
                        </div>


                        <!-- /.input group -->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="register()">Confirm</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- jQuery 2.1.4 -->
<script src="../js/jQuery-2.1.4.min.js"></script>
<script src="../js/jquery-ui.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="../js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="../js/app.min.js"></script>
<script src="../js/prettydate.min.js"></script>
<!-- Socket IO -->
<script src="../js/socket.io.js"></script>
<!-- date-range-picker -->
<script src="../js/moment.min.js"></script>
<script src="../js/daterangepicker.js"></script>
<!-- full calendar -->
<script src="../js/fullcalendar.min.js"></script>
<!-- tag-->
<script src="../js/addtags.js"></script>
<!--alarm-->
<script src="../js/alarm.js"></script>
<!--json2-->
<script src="../js/json2.js"></script>
<!-- bootstrap time picker -->
<script src="../js/bootstrap-timepicker.min.js"></script>
<script>
    console.log('<spring:message code="test"/> ');

    var registerStartDate;
    var registerEndDate;
    var moreview = ${timeline.size()};

    // fullcalendar
    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: ''
        },
        height: 500,
        selectable: true,
        editable: true,
        eventDrop: function (event, delta, revertFunc) {
            if (event.type == 'schedule') {
                var param = {
                    scheduleidx: event.sid, enddate: event.end.format(),
                    startdate: event.start.format()
                };
                var datas = JSON.stringify(param);
                $.ajax({
                    url: "../schedule",
                    type: "PUT",
                    contentType: "application/json; charset=utf-8",
                    data: datas,
                    processData: false,
                    success: function (data) {
                    }
                })
            }
            else if (event.type == 'todolist') {
                var param = {
                    todolistidx: event.tid, enddate: event.end.format(),
                    startdate: event.start.format()
                };
                var datas = JSON.stringify(param);
                $.ajax({
                    url: "../todolist",
                    type: "PUT",
                    contentType: "application/json; charset=utf-8",
                    data: datas,
                    processData: false,
                    success: function (data) {
                    }
                })
            }
        },
        eventResize: function (event, delta, revertFunc) {
            if (event.type == 'schedule') {
                var param = {
                    scheduleidx: event.sid, enddate: event.end.format(),
                    startdate: event.start.format()
                };
                var datas = JSON.stringify(param);
                $.ajax({
                    url: "../schedule",
                    type: "PUT",
                    contentType: "application/json; charset=utf-8",
                    data: datas,
                    processData: false,
                    success: function (data) {
                    }
                })
            }
            else if (event.type == 'todolist') {
                var param = {
                    todolistidx: event.tid, enddate: event.end.format(),
                    startdate: event.start.format()
                };
                var datas = JSON.stringify(param);
                $.ajax({
                    url: "../todolist",
                    type: "PUT",
                    contentType: "application/json; charset=utf-8",
                    data: datas,
                    processData: false,
                    success: function (data) {
                    }
                })
            }
        },
        dayClick: function (date, jsEvent, view) {
            /*
             생성할거임ㅎㅎ
             */

        },
        events: [<c:forEach var="list" items="${schedules}">
            {
                sid: ${list.scheduleidx},
                title: '[${list.project.name}] ${list.content}',
                start: '${list.startdate}',
                place: '${list.place}',
                end: '${list.enddate}',
                type: 'schedule'

            },

            </c:forEach>
                <c:forEach var="list" items="${todolist}">{
                tid:${list.todolistidx},
                title: '[${list.project.name}] ${list.content}',
                start: '${list.startdate}',
                end: '${list.enddate}',
                <c:choose>
                <c:when test="${list.ok=='0'}">
                color: '#EAEAEA',
                </c:when>
                <c:otherwise>
                color: '#5CD1E5',
                </c:otherwise>
                </c:choose>

                type: 'todolist'
            },
            </c:forEach>],
        eventClick: function (event) {
            if (event.type == 'schedule')
                alert(event.place);
            else if (event.type == 'todolist') {
                var par = "id=" + event.tid;
                $.ajax({
                    url: "../todocheck",
                    data: par,
                    dataType: 'text',
                    async: true,
                    type: 'GET',
                    success: function () {
                        if (event.color == '#EAEAEA')
                            event.color = '#5CD1E5';
                        else
                            event.color = '#EAEAEA'
                        $('#calendar').fullCalendar('updateEvent', event);
                    }
                });


            }
        }

    });

    //Date range picker with time picker
    $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'YYYY-MM-DD h:mm A'});
    //Date range as a button
    $('#daterange-btn').daterangepicker({
                ranges: {
                    'Today': [moment(), moment()],
                    'This Month': [moment().startOf('month'), moment().endOf('month')]
                }

            },
            function (start, end) {
                scheduleStart = start;
                scheduleEnd = end;
                $('#daterange-btn span').html(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));
            }
    );

    function toregister(_scheduleIdx, startdate, enddate) {
        scheduleIdx = _scheduleIdx;

        $('#reservation').daterangepicker({
            dateFormat: 'yyyy-MM-dd',
            minDate: new Date(startdate),
            maxDate: new Date(enddate),
            startDate: new Date(startdate),
            endDate: new Date(enddate)
        }, function (start, end) {
            registerStartDate = start;
            registerEndDate = end;
        });
    }
    $('#reservation').on('cancel.daterangepicker', function (ev, picker) {
        $(this).val('');
    });
    $("#Rgmodal").on('hidden.bs.modal', function () {
        $('#reservation').val('');
    });

    /*
     function register() {
     console.log(registerStartDate + " " + registerEndDate);
     var param = "userIdx=${user.useridx} &scheduleIdx=" + scheduleIdx + "&startdate=" + registerStartDate.format('YYYY-MM-DD') + "&enddate=" + registerEndDate.format('YYYY-MM-DD') + "&state=0";

     $.ajax({
     url: "../makeRegister",
     data: param,
     dataType: 'text',
     async: true,
     processData: false,
     contentType: false,
     type: 'GET',
     success: function () {
     location.reload();
     },
     error: function () {
     }
     });
     }
     */
    $(".cb").change(function () {
        var check = $(this);
        var par = "id=" + $(this).val();
        $.ajax({
            url: "../todocheck",
            data: par,
            dataType: 'text',
            async: true,
            type: 'GET',
            success: function () {
                check.parent().toggleClass("done");

            }
        });
    });
    function moreView() {
        var par = "first=" + moreview;
        $.ajax({
            url: "../moreTimeline",
            data: par,
            dataType: 'json',
            async: true,
            type: 'GET',
            success: function (data) {
                moreview += data.length;
            },
            error: function () {

            }


        })
    }


</script>
</body>
</html>