<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> -->
    <!--  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script> -->
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->


    <link rel="stylesheet" href="../css/wait2.css">

    <!-- daterange picker -->
    <link rel="stylesheet" href="../css/daterangepicker-bs3.css">
    <link rel="stylesheet" href="../css/bootstrap-timepicker.min.css">
    <link rel="stylesheet" href="../css/dataTables.bootstrap.css">
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
                            <img src="../${user.img}" class="user-image" alt="">
                            <!-- hidden-xs hides the username on small devices so only the image appears. -->
                            <span class="hidden-xs">${user.id}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- The user image in the menu -->
                            <li class="user-header">
                                <img src="../${user.img}" class="img-circle" alt="User Image">

                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-6 text-center" style="border-right:1px solid;">
                                        <a href="../projectmanager"><i class="fa fa-pencil-square-o"></i> Project
                                            Edit</a>
                                    </div>
                                    <div class="col-xs-6 text-center">
                                        <a href="../userInfo/${user.id}"><i class="fa fa-info-circle"></i> MyInfo
                                            Edit</a>
                                    </div>

                                </div>
                                <!-- /.row -->
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">Profile</a>
                                </div>
                                <div class="pull-right">
                                    <a href="../../" class="btn btn-default btn-flat">Sign out</a>
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
                    <img src="../${user.img}" class="img-circle" alt="User Image">
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
                <li class="treeview">
                    <a href="../courseInfo/${user.id}"><i class="fa fa-university"></i><span> Course Info</span></a>
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
                Main Page
                <!-- <small>Optional description</small>-->
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
                <li class="active">Here</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-8">
                    <div class="box box-success time-line">
                        <div class="box-header">
                            <i class="fa fa-comments-o"></i>

                            <h3 class="box-title">TimeLine</h3>

                            <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
                                <div class="btn-group" data-toggle="btn-toggle">

                                </div>
                            </div> <!-- status -->
                        </div> <!-- header -->
                        <c:forEach var="list" items="${timeline}">
                            <div class="box-body chat" id="chat-box">
                                <!-- chat item -->
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
                </div>
                <!-- TOdo -->
                <section class="col-md-4 connectedSortable">
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="ion ion-clipboard"></i>

                            <h3 class="box-title">To Do List</h3>

                            <div class="box-tools pull-right vec">
                                <ul class="pagination pagination-sm inline vec">
                                    <li><a href="#">&laquo;</a></li>

                                    <li><a href="#">&raquo;</a></li>
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
                                    <span class="text">${list.content}</span>

                                    <small class="label label-danger" prettydate><i
                                            class="fa fa-clock-o"></i>${list.enddate}</small>

                                    <div class="tools">
                                        <i class="fa fa-edit"></i>
                                        <i class="fa fa-trash-o"></i>
                                    </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>


                    <div class="box">
                        <div class="box-header add-header">
                            <i class="fa fa-calendar-plus-o"></i>
                            <h3 class="box-title"> Register Schedule</h3>

                            <div class="box-tools">

                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body table-responsive no-padding" style=" height: 270px; overflow: auto">
                            <table class="table table-hover">
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
                        </div>
                        <!-- /.box-body -->


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
        <!-- Default to the left -->
        <!-- <strong> &copy; 2015 <a href="#">Company</a>.</strong> All rights reserved.-->
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
<!-- tag-->
<script src="../js/addtags.js"></script>
<!--alarm-->
<script src="../js/alarm.js"></script>
<!-- bootstrap time picker -->
<script src="../js/bootstrap-timepicker.min.js"></script>
<script>
    var registerStartDate;
    var registerEndDate;
    var socket;
    $(document).ready(function () {
        socket = io.connect("http://192.168.10.105:9999");
        socket.emit('join', {
            projectIdx: "${project.projectidx}",
            userIdx:${user.useridx},
            userName: "${user.name}",
            userId: "${user.id}",
            userImg: "${user.img}"
        });

        socket.on('alarm', function (data) {
            var par = "userIdx=" +${user.useridx};
            $.ajax({
                url: "../updateAlarm",
                data: par,
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    var size = parseInt($("#alarm-size").text()) + 1;
                    $("#alarm").effect("bounce",{direction:'left',distance:13, times:3},500);
                    $("#alarm-size").text(size);
                    $("#alarm-content").text('You have ' + size + 'notifications');
                    $("#alarm-list").prepend('<li id="alarm-"' + data.alarmidx + '><a href="#">' +
                            '<i class="fa fa-users text-aqua"></i><strong>' + data.actorid + '</strong>' +
                            'has invited you to <strong>' + data.projectname + '</strong>' +
                            '<div style="float:right;">' +
                            ' <button type="button" class="btn btn-primary btn-xs"' +
                            'onclick=accept("' + data.alarmidx + '")>Ok</button>' +
                            '<button type="button" class="btn btn-default btn-xs"' +
                            'onclick=decline("' + data.alarmidx + '")>Cancel' +
                            '</button>' +
                            '</div>' +
                            '</a>' +
                            '</li>');
                }
            });
        });
    });// socket end
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

    $(".cb").change(function () {
        var check = $(this);
        var par = "id=" + $(this).val();

        $.ajax({
            url: "../todocheck",
            data: par,
            dataType: 'text',
            async: true,
            processData: false,
            contentType: false,
            type: 'GET',
            success: function () {
                check.parent().toggleClass("done");
            }
        });
    });


</script>
</body>
</html>