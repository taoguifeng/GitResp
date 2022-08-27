<template>
  <div>
    <el-table ref="productTable" :data="list" style="width: 100%" border>
      <el-table-column label="编号" width="100" align="center" prop="id">
        <template #default="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column label="菜品图片" width="120" align="center">
        <template #default="scope"
          ><img style="height: 80px" :src="scope.row.dishImg"
        /></template>
      </el-table-column>

      <el-table-column label="菜品名称" align="center">
        <template #default="scope">{{ scope.row.name }}</template>
      </el-table-column>

      <el-table-column label="价格" sortable="" width="120" align="center">
        <template #default="scope">价格：￥{{ scope.row.price }}</template>
      </el-table-column>

      <el-table-column label="标签" width="140" align="center">
        <template #default="scope">
          <el-switch
            @change="handleSwitchChange(scope, 'update_publish_status')"
            active-text="上架"
            :active-value="1"
            :inactive-value="0"
            v-model="scope.row.publishStatus"
          >
          </el-switch>
          <el-switch
            @change="handleSwitchChange(scope, 'update_recommend_status')"
            active-text="推荐"
            :active-value="1"
            :inactive-value="0"
            v-model="scope.row.recommendStatus"
          >
          </el-switch>
          <el-switch
            @change="handleSwitchChange(scope, 'update_new_status')"
            active-text="新品"
            :active-value="1"
            :inactive-value="0"
            v-model="scope.row.newStatus"
          >
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column sortable label="销量" width="100" align="center">
        <template #default="scope">{{ scope.row.sale }}</template>
      </el-table-column>

      <el-table-column label="操作" width="160" align="center">
        <template #default="scope">
          <el-button size="mini" @click="handleUpdateDish(scope.row.id)"
            >编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDeleteDish(scope.$index, scope.row)"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {
  dishList,
  update_new_status,
  update_publish_status,
  update_recommend_status,
} from "../../../api/dish";
import { Edit } from "@element-plus/icons-vue";

export default {
  data() {
    return {
      list: [],
      Edit,
      dialogVisible: false,
    };
  },
  methods: {
    handleUpdateDish(id) {
      this.$router.push({
        path: "/dishForm",
        query: { id },
      });
    },
    async handleSwitchState(data, updateType) {
      console.log(data);
      switch (updateType) {
        case "update_new_status":
          await update_new_status(data);
          break;
        case "update_publish_status":
          await update_publish_status(data);
          break;
        case "update_recommend_status":
          await update_recommend_status(data);
          break;
      }
      this.fetchData();
    },
    handleSwitchChange(data, type) {
      const item = data.row;
      switch (type) {
        case "update_new_status":
          this.handleSwitchState(
            {
              dishIds: item.id,
              newStatus: item.newStatus,
            },
            type
          );
          break;
        case "update_publish_status":
          this.handleSwitchState(
            {
              dishIds: item.id,
              publishStatus: item.publishStatus,
            },
            type
          );
          break;
        case "update_recommend_status":
          this.handleSwitchState(
            {
              dishIds: item.id,
              recommendStatus: item.recommendStatus,
            },
            type
          );
          break;
      }
    },
    fetchData() {
      dishList({
        pageNum: 1,
        pageSize: 10,
      }).then((res) => {
        this.list = res.arrays;
      });
    },
  },
  created() {
    this.fetchData();
  },
};
</script>

<style></style>
