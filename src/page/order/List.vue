<template>
  <div>
    <el-table
      ref="orderTable"
      :data="list"
      style="width: 100%"
      @selection-change="handleSelectionChange"
      v-loading="listLoading"
      border
      :default-sort="{ prop: 'id', order: 'ascending' }"
    >
      <el-table-column label="订单编号" align="center" prop="id" sortable>
        <template slot-scope="scope">{{ scope.row.id }}</template>
      </el-table-column>

      <el-table-column label="提交时间" align="center" prop="date" sortable>
        <template slot-scope="scope">{{
          scope.row.createTime | formatCreateTime
        }}</template>
      </el-table-column>

      <el-table-column label="用户ID" align="center" sortable prop="userId">
        <template slot-scope="scope">{{ scope.row.userId }}</template>
      </el-table-column>

      <el-table-column label="消费门店 / 桌号" align="center">
        <template slot-scope="scope"
          >{{ scope.row.storeId | formatStoreName(options) }}/
          {{ scope.row.table + "桌" }}
        </template>
      </el-table-column>

      <el-table-column label="消费方式" align="center">
        <template slot-scope="scope"
          >{{ scope.row.consumeType | formatConsumeType }}
        </template>
      </el-table-column>

      <el-table-column
        label="待支付/支付金额(实)"
        align="center"
        prop="qian"
        sortable
      >
        <template slot-scope="scope"
          >￥{{
            scope.row.otherFee +
            scope.row.originalPrice -
            scope.row.shopDiscount -
            scope.row.couponDiscount
          }}</template
        >
      </el-table-column>

      <el-table-column label="取餐号" align="center" sortable>
        <template slot-scope="scope">{{
          scope.row.fetchMealCode ? scope.row.fetchMealCode : "无"
        }}</template>
      </el-table-column>

      <el-table-column label="订单状态" align="center">
        <template slot-scope="scope">{{
          scope.row.orderType | formatOrderType
        }}</template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { orderList } from "../../api/order";
export default {
  data() {
    return {
      list: [],
    };
  },
  methods: {},
  created() {
    const params = {
      pageNum: 1,
      pageSize: 100000,
      storeId: -1,
    };
    if (this.$route.query.orderType) {
      params.orderType = this.$route.query.orderType;
    }
    orderList(params).then(({ arrays }) => (this.list = arrays));
  },
};
</script>

<style></style>
