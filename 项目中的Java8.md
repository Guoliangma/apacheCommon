# 集约化中的Java8 新特性

### 一、空数据处理

#### **1.Optional.ofNullable **

````
List<ZxWj> zxWjList = message.getObject(BODY, new TypeReference<List<ZxWj>>() {});
Optional.ofNullable(zxWjList).ifPresent(data -> {
	Map<String, List<ZxWj>> wjMap = 		 	data.stream().collect(Collectors.groupingBy(ZxWj::getAjBh));
wjMap.forEach((k, v) -> zdrjService.rj(null, v));
});
````

#### 2.orElse() & orElseGet()

````
List<ZxWs> list = Optional.ofNullable(zxwsMapper.listByConditionV2(ajbh, ywbhs, ywlxs))
				.orElse(new ArrayList<>());
````

```java
List<CcbxPgVo> list = Optional.ofNullable(pgxxs.getList()).orElseGet(ArrayList::new);
```

问题描述

- orElse里面的方法必然会执行，orElseGet只有前面为空才会执行，建议使用orElseGet
- 如果orElse涉及到数据库或者计算之类的操作，会很浪费性能和时间
- server里有大量的orElse使用，建议抽时间修改

```java
String separator = ",";
Arrays.asList( "a", "b", "d" ).forEach(
    ( String e ) -> System.out.print( e + separator ) );
```



### 二、集合排序

### 1.Sorted

正序：

```java
List<JzysmlVO> roots = zxclmlList
            .stream()
    		.filter(ml -> ml.getPid() == null)
            .sorted(Comparator.comparing(ZxClml::getXssx))
            .map(this::zxclmlToJzysmlVO)
            .collect(Collectors.toList());
```

默认排序的倒序：

```xml
List<CcbxYwdz> list = select(CcbxYwdz.builder().bhAj(query.getAjbh()).build())
            .stream()
            .sorted(Comparator.comparing(CcbxYwdz::getZdrq).reversed())
            .collect(Collectors.toList());
```

````java
Map<LocalDate, List<ExamRecord>> examRecordByDate = examRecordService.getListByParam(recordQueryParam)
    		.stream()
            .filter(examRecord -> !Objects.equals(examRecord.getKsBh(), needFirstExamContext.getCommonExamCategoryBh()))
            .collect(Collectors.groupingBy(ExamRecord::getKsrq));
return examRecordByDate.entrySet()
    .stream()
    .sorted((entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey()))
    .map(this::examRecord2QualificationVO).collect(Collectors.toList());
````

### 三、集合转换

#### 1.集合转Map

````
Map<String, String> wsrjztMap = wsList
		.stream()
		.collect(Collectors.toMap(ZdrjhdDTO.RjxxListBean::getCFileId, 			  ZdrjhdDTO.RjxxListBean::getRjzt, (k1, k2) -> k1));
````

```java
Map<String, ZxDsrVO> zxDsrVOMap = zxDsrVOList.stream().collect(Collectors.toMap(ZxDsrVO::getBh, z -> z));
```

````
Map<String, List<ZxZxxz>> xzMap = querylist.getList().stream().collect(Collectors.groupingBy(ZxZxxz::getBh));
````

#### 2.转Set 

```java
Set<String> bhList = zxwjList.stream().map(ZxWj::getBh).collect(Collectors.toSet());
```

#### 3.循环 

```java
dsrxx.getXgrVoList().stream().forEach(xgrxx -> updateSscyr(dsrxx.getJbfy(), xgrxx));
```

stream的forEach，没有List直接forEach效率高

#### 4.去重

````
List<String> bhs = list.stream().map(YcsxDto::getBh).distinct().collect(Collectors.toList());
````

#### 5.数据处理

数据相加

```java
BigDecimal khzje = khs
    .stream()
    .map(CcbxKh::getKhje)
    .reduce(BigDecimal.ZERO, BigDecimal::add);
```

#### 6.flatMap

````
List<User> uList = Lists.newArrayList();
        User u1 = new User();
        u1.setAddr("a1;a2;a3;a4;a5");
 
        User u2 = new User();
        u2.setAddr("b1;b2;b3;b4;b5");
 
        uList.add(a);
        uList.add(b);
 
        List<String> addrList = uList
        .stream()
        .map(x -> x.getAddr())
        .flatMap(x->Arrays.stream(x.split(";")))
        .collect(Collectors.toList());
        //或者
        List<String> ridStrList = uList
        .stream()
        .map(x -> x.getAddr())
        .map(x -> x.split(";"))
        .flatMap(Arrays::stream)
        .collect(Collectors.toList());
        System.out.println(addrList);
````

### 四、并发

#### 1.CompletableFuture

```javascript
CopyOnWriteArrayList<InputStream> streams = Lists.newCopyOnWriteArrayList();
        CompletableFuture.allOf(
                urls.stream().map(url ->
                        CompletableFuture.supplyAsync(() ->
                                TCSUtils.downloadTCS(url), EXECUTOR
                        ).whenComplete((s, e) -> streams.add(s))
                ).toArray(CompletableFuture[]::new)
        ).join();
```

#### 2.parallel

````java
kpzb.stream().parallel().forEach(zb -> {
                List<Kpdxzb> dxzbs = initKpdxzbs(kpdxs, zb);
                if (Objects.equals(Code.KPZB_NODETYPE_KPZB, zb.getName())) {
                    dxzbs.stream().forEach(dxzb -> zb.setName("总分"));
                    kpdxzbService.batchSave(dxzbs);
                    return;
                }
                List<Jszb> jszbList = getGrJszbs(zb);
                List<Jszb> jszbBmList = getBmJszbs(zb);
                // 定义部门使用数据map
                Map<String, List<Grzb>> deptValueMap = new HashMap<>(72);
                // 个人指标收集
                handleGrCollect(kpjh, kptx, zb, jszbList, jszbBmList, dxzbs, deptValueMap);
                // 部门数据收集处理
                handleBmCollect(jszbBmList, dxzbs, deptValueMap, kpjh);
                kpdxzbService.batchSave(dxzbs);
            });
````

当遍历集合做重复的操作时，如果使用串行执行会相当耗时，可以考虑采用多线程提速。或者使用Java8的parallelStream().forEach(),并行遍历循环,但程序在运行过程中具体会使用多少个线程进行处理,系统会根据运行服务器的资源进行分配。

### 五、数据过滤

#### 1.Filter

````java
JSONArray array = jsonArray.stream().filter(json->info.getName().equals(
                        ((JSONObject)json).getString("tableName"))).collect(Collectors.toCollection(JSONArray::new));
                DataTable table=new DataTable(info.getName());
````

````java
 Predicate<WtFywd> fyFilter = x -> ObjectUtils.equals(x.getDj(), WtConstants.LEVEL_SECOND) && ObjectUtils
                .equals(x.getGy(), wtFywd.getGy());
            List<WtFywd> levelFirstSon = searchList.stream().filter(fyFilter).collect(Collectors.toList());
````


#### 2.anyMatch

````java
staticFileSuffixes.stream().anyMatch(each -> Objects.equals(suffix, each));
````