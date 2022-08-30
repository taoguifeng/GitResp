<template>
  <div>
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      label-width="80px"
      class="form"
    >
      <el-form-item label-width="100px" label="店铺名称:" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="店铺图片:" prop="img">
        <el-input
          v-model="form.img"
          placeholder="请填写图片URL，如您需要上传本地图片，请点击下方按钮上传"
        ></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="店铺桌位:">
        <el-input-number
          v-model="tableNum"
          @change="handleChange"
          :min="0"
          label="选择数量"
        ></el-input-number>
        <el-table
          :data="form.tables"
          stripe
          style="width: 100%"
          v-show="tableNum > 0"
        >
          <el-table-column prop="date" label="桌号" width="180">
            <template #default="scope">
              <el-input disabled v-model="scope.row.table"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="可容纳人数" width="180">
            <template #default="scope">
              <el-input
                v-model="scope.row.capacity"
                placeholder="请输入内容"
              ></el-input>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>

      <el-form-item label-width="100px" label="店铺地址:" prop="price">
        <el-input
          v-model.number="form.address"
          placeholder="请填写店铺地址"
        ></el-input>
      </el-form-item>

      <el-form-item
        label-width="100px"
        label="店铺营业时间:"
        prop="description"
      >
        <el-input
          v-model.number="form.time"
          placeholder="请填写店铺营业时间"
        ></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="店铺联系电话:" prop="main">
        <el-input v-model="form.contactPhone"></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="开启外卖配送: ">
        <el-switch
          v-model="form.supportTakeout"
          active-value="1"
          inactive-value="0"
          active-text="开启"
          inactive-text="不开启"
        >
        </el-switch>
      </el-form-item>

      <el-form-item style="margin-left: 20px">
        <el-button type="primary" @click="handleSubmit">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { createStore, updateStore, getStore } from "../../api/store";

export default {
  data() {
    return {
      form: {
        tables: [],
      },
      tableNum: 0,
    };
  },
  methods: {
    handleChange() {
      let num = this.tableNum,
        cnt = Math.abs(num - this.form.tables.length);
      console.log(num, cnt);
      for (let i = cnt - 1; i >= 0; i--) {
        if (num > this.form.tables.length)
          this.form.tables.push({ table: num - i, capacity: null });
        else this.form.tables.pop();
      }
    },
    handleSubmit() {
      if (this.form.id) {
        updateStore(this.form).then(() =>
          this.$message.success("修改店铺信息成功")
        );
      } else {
        createStore(this.form).then(() =>
          this.$message.success("修改店铺信息成功")
        );
      }
    },
  },
  created() {
    if (this.$route.query.id) {
      getStore(this.$route.query.id).then((res) => {
        this.form = res
        this.tableNum = this.form.tables.length
      });
    }
  },
};
</script>

<style></style>
