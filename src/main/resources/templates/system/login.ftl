<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=yes">
    <title>绩效管理平台后台管理系统</title>
    <link rel="shortcut icon" type="image/x-icon" href="adminlte/dist/img/favicon.ico">

    <!--[if IE]>
	<script src="http://libs.useso.com/js/html5shiv/3.7/html5shiv.min.js"></script>
    <![endif]-->


    <style type="text/css">
        .flow-wrapper{
            position: absolute;
            bottom: -100px;
            top:0px;
            margin: auto;
        }
        #Stage{
            background-color: rgb(57, 173, 205) !important;
        }
        #bg{
            height: 40%;
            position: absolute;
            width: 100%;
            bottom:0px;;
        }
        .login{
            background: none;
            color: #575757;
            text-align:center;
            margin-top: 300px;
        }
    </style>
</head>
<body style=" background-color: rgb(57, 173, 205) !important;">

<div class="cont" style="background-image:none;">
        <div class="login">
            <h1>绩效管理平台后台管理系统</h1>
            <form action="${request.contextPath}/postLogin" class="login__form" method="post">
                <!--默认账号密码user、admin、super-->
			<#--<input type="hidden" name="username" />-->
			<#--<input type="hidden" name="password" />-->
                <div class="login__row" >
                    <!--账号-->
                    <input type="text" name="username" class="login__input name" placeholder="Username"/>
                    <p/>
                    <!--密码-->
                    <input type="password" name="password" class="login__input pass" placeholder="Password"/>
                </div>
                <p>${failed!}</p>
                <button type="submit" class="login__submit">登 录</button>

            </form>
        </div>

</div>


<!-- jQuery 2.2.3 -->
<script src="adminlte/plugins/jQuery/jquery-2.2.3.min.js"></script>

<script>
    $(document).ready(function () {
        var animating = false, submitPhase1 = 1100, submitPhase2 = 400, logoutPhase1 = 800, $login = $('.login'), $app = $('.app');
        function ripple(elem, e) {
            $('.ripple').remove();
            var elTop = elem.offset().top, elLeft = elem.offset().left, x = e.pageX - elLeft, y = e.pageY - elTop;
            var $ripple = $('<div class=\'ripple\'></div>');
            $ripple.css({
                top: y,
                left: x
            });
            elem.append($ripple);
        }
        ;
        $('.login__form,.reg__form').on('submit', function (e) {
            if (animating)
                return false;
            animating = true;
            var that = $(".login__submit");
            ripple($(that), e);
            $(that).addClass('processing');
            setTimeout(function () {
                animating = false;
                that.removeClass('processing');
            }, submitPhase1);
            return true;
        });

        $(".login__signup a").on('click', function (e) {
            var that = $(".login__submit");
            that.addClass('processing success');
            setTimeout(function () {
                $app.show();
                $app.css('top');
                $app.addClass('active');
            }, submitPhase2 - 70);
            setTimeout(function () {
                $login.hide();
                $login.addClass('inactive');
                animating = false;
                that.removeClass('success processing');
            }, submitPhase2);
            return false;
        });

        $(document).on('click', '.app__logout', function (e) {
            if (animating)
                return;
            $('.ripple').remove();
            animating = true;
            var that = $(".login__submit");
            that.addClass('processing success');
            setTimeout(function () {
                $app.removeClass('active');
                $login.show();
                $login.css('top');
                $login.removeClass('inactive');
            }, submitPhase2 - 70);
            setTimeout(function () {
                $app.hide();
                animating = false;
                that.removeClass('success processing');
            }, submitPhase2);
        });
    });
</script>
</body>
</html>