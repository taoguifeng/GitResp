<template>
  <div>
    <el-form inline>
      <el-form-item label="搜索">
        <el-input placeholder="请输入搜索内容" v-model="search"></el-input>
      </el-form-item>
    </el-form>
    <el-table
      ref="productTable"
      :data="list2"
      style="width: 100%"
      :default-sort="{ prop: 'id', order: 'ascending' }"
      v-loading="listLoading"
      border
    >
      <el-table-column
        label="编号"
        width="100"
        align="center"
        prop="id"
        sortable=""
      >
        <template #default="scope">{{ scope.row.id }}</template>
      </el-table-column>

      <el-table-column label="套餐名称" align="center">
        <template #default="scope">{{ scope.row.name }}</template>
      </el-table-column>

      <el-table-column
        label="价格"
        width="120"
        align="center"
        sortable
        prop="price"
      >
        <template #default="scope">价格：￥{{ scope.row.price }}</template>
      </el-table-column>

      <el-table-column label="标签" width="140" align="center">
        <template #default="scope">
          <el-switch
            @change="handlePublishStatusChange(scope.$index, scope.row)"
            active-text="上架"
            :active-value="1"
            :inactive-value="0"
            v-model="scope.row.publishStatus"
          >
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column
        label="销量"
        width="100"
        align="center"
        sortable
        prop="sale"
      >
        <template #default="scope">{{ scope.row.sale }}</template>
      </el-table-column>

      <el-table-column label="操作" width="160" align="center">
        <template #default="scope">
          <el-button size="mini" @click="handleUpdate(scope.row.id)"
            >编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { comboList, update_publish_status, delCombo } from "../../../api/combo";
import { Edit } from "@element-plus/icons-vue";

export default {
  data() {
    return {
      list: [],
      Edit,
      search: "",
    };
  },
  computed: {
    list2() {
      return this.list.filter((item) => {
        return item.name.match(this.search);
      });
    },
  },
  methods: {
    fetchData() {
      comboList({
        pageNum: 1,
        pageSize: 100000,
      }).then((res) => {
        this.list = res.arrays;
      });
    },
    handlePublishStatusChange(i, row) {
      update_publish_status({
        comboIds: row.id,
        publishStatus: row.publishStatus,
      });
    },
    handleDelete(i, row) {
      delCombo(row.id).then(() => {
        this.fetchData();
        this.$message.success("删除成功");
      });
    },
    handleUpdate(id) {
      this.$router.push({
        path: "/comboForm",
        query: { id },
      });
    },
  },
  created() {
    this.fetchData();
  },
};
</script>

<style></style>
