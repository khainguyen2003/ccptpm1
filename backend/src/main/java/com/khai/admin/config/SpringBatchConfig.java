//package com.khai.admin.config;
//
//import com.khai.admin.entity.Product;
//import com.khai.admin.entity.User;
//import com.khai.admin.processor.UserProcessor;
//import com.khai.admin.repository.ProductRepository;
//import com.khai.admin.repository.UserRepository;
//import com.khai.admin.service.FileService;
//import com.khai.admin.util.ColumnRangePartitioner;
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.annotation.AfterJob;
//import org.springframework.batch.core.annotation.AfterStep;
//import org.springframework.batch.core.annotation.BeforeStep;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobScope;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.partition.PartitionHandler;
//import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.skip.SkipPolicy;
//import org.springframework.batch.extensions.excel.RowMapper;
//import org.springframework.batch.extensions.excel.mapping.PassThroughRowMapper;
//import org.springframework.batch.extensions.excel.poi.PoiItemReader;
//import org.springframework.batch.extensions.excel.streaming.StreamingXlsxItemReader;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableBatchProcessing
//public class SpringBatchConfig {
//    @Autowired
//    private UserRepository userRepository;
//    private StepExecution stepExecution;
//    @Autowired
//    private FileService fileService;
//
////    @Bean
////    @StepScope
////    public FlatFileItemReader<Product> fileProductreader(@Value("#{jobParameters[fileName]}") String inputFileName) {
////        return new FlatFileItemReaderBuilder<Product>()
////                .name("productItemReader")
////                .resource(new ClassPathResource(inputFileName))
////                .delimited()
////                .names(columnNames(inputFileName))
////                .targetType(Product.class)
////                .build();
////    }
//
//    @Bean
//    @StepScope
//    public PoiItemReader excelProductReader(@Value("#{jobParameters[fileName]}") String inputFileName) {
//        PoiItemReader reader = new PoiItemReader();
//        reader.setResource(new FileSystemResource(inputFileName));
//        reader.setRowMapper(rowMapper());
//        reader.setName("excelReader");
//        return reader;
//    }
//
//    @Bean
//    @StepScope
//    public StreamingXlsxItemReader StreamingExcelReader(RowMapper rowMapper, @Value("#{jobParameters['fileName']}") String inputFileName) {
//        StreamingXlsxItemReader reader = new StreamingXlsxItemReader();
//        reader.setResource(new FileSystemResource(inputFileName));
//        reader.setRowMapper(rowMapper);
//        return reader;
//    }
//
//    @Bean
//    public RowMapper rowMapper() {
//        return new PassThroughRowMapper();
//    }
//
//    @Bean
//    public ProductItemProcessor processor() {
//        return new ProductItemProcessor();
//    }
//
//    @Bean
//    public JdbcBatchItemWriter<Product> ProductWriter(DataSource dataSource) {
//        return new JdbcBatchItemWriterBuilder<Product>()
//                .sql("INSERT INTO tblproduct (product_name, product_desc) VALUES (:name, :description)")
//                .dataSource(dataSource)
//                .beanMapped()
//                .build();
//    }
//
//    @Bean
//    public RepositoryItemWriter<Product> writer(ProductRepository productRepository) {
//        RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();
//        writer.setRepository(productRepository);
//        writer.setMethodName("save");
//        return writer;
//    }
//    // end::readerwriterprocessor[]
//
//    // tag::jobstep[]
//    @Bean
//    public Job importProductJob(JobRepository jobRepository, Step startImportExcelStep, JobCompletionNotificationListener listener) {
//        return new JobBuilder("importProductJob", jobRepository)
//                .listener(listener)
//                .start(startImportExcelStep)
//                .build();
//    }
//    @Bean
//    public Step slaveStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
//                          PoiItemReader excelProductReader, ProductItemProcessor processor, JdbcBatchItemWriter<Product> writer) {
//        return new StepBuilder("slaveStep", jobRepository)
//                .<Product, Product> chunk(10, transactionManager)
//                .reader(excelProductReader)
//                .processor(processor)
//                // Xử lý trường hợp gặp phải lỗi
//                .faultTolerant()
//                .skipPolicy(skipPolicy())
//                .skipLimit(2) // chỉ định số lần skip tối ta. nếu vượt quá sẽ ném lỗi và dừng
////                .skip(NumberFormatException.class) // Khi gặp phải lỗi chỉ định sẽ bỏ qua dòng lỗi thay vì ném lỗi và dừng
//                // end
//                .writer(writer)
//                .build();
//    }
//    @Bean
//    public Step startImportExcelStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
//                                     PoiItemReader excelReader, ProductItemProcessor processor, RepositoryItemWriter<Product> writer) {
//        return new StepBuilder("startImportExcelStep", jobRepository)
//                .<Product, Product> chunk(10, transactionManager)
//                .reader(excelReader)
//                .processor(processor)
//                // Xử lý trường hợp gặp phải lỗi
//                .faultTolerant()
//                .skipPolicy(skipPolicy())
//                .skipLimit(2) // chỉ định số lần skip tối ta. nếu vượt quá sẽ ném lỗi và dừng
////                .skip(NumberFormatException.class) // Khi gặp phải lỗi chỉ định sẽ bỏ qua dòng lỗi thay vì ném lỗi và dừng
//                // end
//                .writer(writer)
//                .build();
//    }
//
//    @Bean
//    public ColumnRangePartitioner partitioner() {
//        return new ColumnRangePartitioner();
//    }
//
//    @Bean
//    public PartitionHandler partitionHandler(Step slaveStep) {
//        TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
//        taskExecutorPartitionHandler.setGridSize(4);
//        taskExecutorPartitionHandler.setTaskExecutor(taskExecutor());
//        taskExecutorPartitionHandler.setStep(slaveStep);
//        return taskExecutorPartitionHandler;
//    }
//
////    /**
////     * Cấu hình dùng cho phân vùng batch
////     * @param jobRepository
////     * @param slaveStep
////     * @return
////     */
////    @Bean
////    public Step masterStep(JobRepository jobRepository, Step slaveStep) {
////        return new StepBuilder("masterStep", jobRepository)
////                .partitioner(slaveStep.getName(), partitioner())
////                .partitionHandler(partitionHandler(slaveStep))
////                .build();
////    }
//
//    public SkipPolicy skipPolicy() {
//        return new ExceptionSkipPolicy();
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setMaxPoolSize(4);
//        taskExecutor.setCorePoolSize(4);
//        taskExecutor.setQueueCapacity(4);
//        return taskExecutor;
//    }
//
//    @AfterJob
//    public void afterJob(JobExecution jobExecution){
//        if (jobExecution.getStatus() == BatchStatus.COMPLETED ) {
//            //job success
//        }
//        else if (jobExecution.getStatus() == BatchStatus.FAILED) {
//            //job failure
//        }
//    }
//
//    // Phương thức này khởi tạo và trả về tên cột từ tệp
//    private String[] columnNames(String inputFileName) {
//
//        return fileService.getColumnNames(inputFileName);
//    }
//    // end::jobstep[]
//}
