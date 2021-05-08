<div class="row">
    <div class="col-md-12">
        <form id="tNoticeMgrAddForm">
            <div class="modal-body">
                <div class="form-group">
                    <label id="tNoticeMgr_add_titleLabel">公告标题</label>
                    <input type="text" class="form-control" name="title" id="tNoticeMgr_add_title" placeholder="输入公告标题...">
                </div>
                <div class="form-group">
                    <label id="tNoticeMgr_add_subtitleLabel">公告副标题</label>
                    <input type="text" class="form-control" name="subtitle" id="tNoticeMgr_add_subtitle" placeholder="输入公告副标题...">
                </div>

                <div class="form-group">
                    <label id="tNoticeMgr_add_noticeContentLabel">公告发布内容</label>
                    <!-- 加载编辑器的容器 -->
                    <script id="container" name="noticeContent" type="text/plain">请在这里写你的公告发布内容</script>
                    <script type="text/javascript">

                    </script>
                    <#--<input type="hidden" id="tNoticeMgr_add_noticeContent" name="noticeContent" >-->
                   <#-- <!-- 实例化编辑器 &ndash;&gt;
                   -->
                  <#--  <input type="text" class="form-control" name="noticeContent" id="tNoticeMgr_add_noticeContent"
                           placeholder="输入公告发布内容...">-->
                </div>

                <div class="form-group">
                    <label id="tNoticeMgr_add_createorLabel">公告发布人</label>
                    <input type="text" class="form-control" name="createor" id="tNoticeMgr_add_createor"
                    value="${user.name}" readonly="true">
                </div>

                <div class="form-group">
                    <label id="tNoticeMgr_add_isTopList" >是否置顶</label>
                    <select name="isTop"class="form-control select2" style="width: 100%;">
                        <option selected="selected" value="0">不置顶</option>
                        <option value="1">置顶</option>
                    </select>
                </div>


            </div>
            <div class="modal-footer">
                <div class="pull-right">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                                class="fa fa-close"></i>取消
                    </button>
                    <button type="button" class="btn btn-primary btn-sm" onclick="tNoticeMgrAdd();"><i
                                class="fa fa-save"></i>保存
                    </button>
                </div>
            </div>

    </form>
</div>
</div>
<script type="text/javascript">

    UE.delEditor('container');//每次使用时先删除一下，避免之前已经实例化造成的渲染失败
    var ue = UE.getEditor('container');//实体类编辑器

    //1.添加的保存按钮
    function tNoticeMgrAdd() {
        $("span").remove(".errorClass");
        $("br").remove(".errorClass");
        var status = 1;
        //读取编辑器的内容
        ue.ready(function(){
            //获取内容
            var txt = ue.getContent();
            alert(txt);
            if(txt==''){
                $("#tNoticeMgr_add_noticeContentLabel").prepend('<span class="errorClass" style="color:red">*公告内容不能为空</span><br class="errorClass"/>');
                status = 0;
            }
            /*else{
                $("#tNoticeMgr_add_noticeContent").val()
                $("#tNoticeMgr_add_noticeContent").attr(value,txt); // 给神秘属性赋值
            }*/

        });

        if ($("#tNoticeMgr_add_title").val() == "") {
            $("#tNoticeMgr_add_titleLabel").prepend('<span class="errorClass" style="color:red">*公告标题不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if ($("#tNoticeMgr_add_subtitle").val() == "") {
            $("#tNoticeMgr_add_subtitleLabel").prepend('<span class="errorClass" style="color:red">*公告副标题不能为空</span><br class="errorClass"/>');
            status = 0;
        }
       /* if ($("#tNoticeMgr_add_noticeContent").val() == "") {
            $("#tNoticeMgr_add_noticeContentLabel").prepend('<span class="errorClass" style="color:red">*公告内容不能为空</span><br class="errorClass"/>');
            status = 0;
        }*/

        if (status == 0) {
            return false;
        } else {
            $.ajax({
                url: '${request.contextPath}/tNoticeMgr/save',
                type: 'post',
                dataType: 'text',
                data: $("#tNoticeMgrAddForm").serialize(),
                success: function (data) {
                    var json = JSON.parse(data);
                    if (json.status) {
                        $("#lgModal").modal('hide');
                        alertMsg("添加成功", "success");
                        reloadTable(list_ajax, "#roleTime", "#rolePremise");

                        //摧毁模态框
                        $('#lgModal').on('hidden', function() {
                            $(this).val(null);
                        });
                        $("#lgModal").dialog("destroy");//摧毁模态框

                    } else {
                        alertMsg("添加失败:" + json.msg, "success");

                        //摧毁模态框
                        $('#lgModal').on('hidden', function() {
                            $(this).val(null);
                        });
                        $("#lgModal").dialog("destroy");//摧毁模态框
                    }
                }

            })
        }
    }
</script>