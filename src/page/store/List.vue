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
      v-loading="listLoading"
      border
    >
      <el-table-column
        type="selection"
        width="60"
        align="center"
      ></el-table-column>

      <el-table-column label="店铺图片" width="120" align="center">
        <template #default="scope"
          ><img style="height: 80px" :src="scope.row.img"
        /></template>
      </el-table-column>

      <el-table-column label="店铺名称" align="center">
        <template #default="scope">{{ scope.row.name }}</template>
      </el-table-column>

      <el-table-column label="店铺地址" width="120" align="center">
        <template #default="scope">{{ scope.row.address }}</template>
      </el-table-column>

      <el-table-column label="标签" width="140" align="center">
        <template #default="scope">
          <p>
            <el-switch
              @change="handleStoreWork(scope.$index, scope.row)"
              active-text="店铺营业"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.working"
            >
            </el-switch>
          </p>
          <p>
            <el-switch
              @change="handlesupportTakeout(scope.$index, scope.row)"
              active-text="开启外卖"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.supportTakeout"
            >
            </el-switch>
          </p>
        </template>
      </el-table-column>

      <el-table-column label="营业时间" width="100" align="center">
        <template #default="scope">{{ scope.row.time }}</template>
      </el-table-column>

      <el-table-column label="联系方式" width="100" align="center">
        <template #default="scope">{{ scope.row.contactPhone }}</template>
      </el-table-column>

      <el-table-column label="操作" width="160" align="center">
        <template #default="scope">
          <el-button size="mini" @click="handleUpdate(scope.$index, scope.row)"
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
import {
  storeList,
  deleteStore,
  update_working_status,
  update_takeout_status,
} from "../../api/store";

export default {
  data() {
    return {
      list: [],
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
      storeList().then(({ arrays }) => (this.list = arrays));
    },
    handleDelete(i, row) {
      deleteStore(row.id).then(() => {
        this.fetchData();
        this.$message.success("删除店铺成功");
      });
    },
    handleStoreWork(i, row) {
      update_working_status({
        storeIds: row.id,
        working: row.working,
      });
    },
    handlesupportTakeout(i, row) {
      console.log(row);
      update_takeout_status({
        storeIds: row.id,
        takeoutStatus: row.supportTakeout,
      });
    },
    handleUpdate(i, row) {
      this.$router.push({
        path: "/storeForm",
        query: {
          id: row.id,
        },
      });
    },
  },
  created() {
    this.fetchData();
  },
};
</script>

<style></style>
