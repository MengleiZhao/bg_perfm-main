<div class="row">
	<div class="col-md-12">
		<div class="box-body  no-padding">
			<table class="table table-striped">
                <tr>
                	<td>调研提纲id：</td>
                	<td style="width: 90%">
						<#if tResearchOutlineTempView.idX??>
							${tResearchOutlineTempView.idX}
						<#else>
							无
						</#if>
					</td>
                </tr>

                <tr>
                	<td>调研提纲序号：</td>
                	<td>
						<#if tResearchOutlineTempView.orderNo??>
							${tResearchOutlineTempView.orderNo}
						<#else>
							无
						</#if>
					</td>
                </tr>

                <tr>
                	<td>调研提纲名称：</td>
					<td>
						<#if tResearchOutlineTempView.outlineName??>
							${tResearchOutlineTempView.outlineName}
						<#else>
							无
						</#if>
					</td>
                </tr>
                

                
                <tr>
                	<td>调研提纲时间：</td>
                	<td>
						<#if tResearchOutlineTempView.createTime??>
							${tResearchOutlineTempView.createTime?string('yyyy-MM-dd')}
						<#else>
							无
						</#if>
					</td>
                </tr>
                
                 <tr>
                	<td>调研提纲创建人：</td>
                	<td>
						<#if tResearchOutlineTempView.createor??>
							${tResearchOutlineTempView.createor}
						<#else>
							无
						</#if>
					</td>
                </tr>



         	</table>
         	<div class="box-footer">
				<div class="pull-right">
					<button type="button" class="btn btn-default btn-sm" id="close" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
				</div>
			</div>
    	</div>
	</div>
</div>