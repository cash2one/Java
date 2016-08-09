namespace java com.haizhi.thrift

struct KkConf
{
	1: i32 Status,
	2: i32 Threshhold,
	3: i32 RequestTime,
	4: i32 RepeatTimes
}


service KkLog
{
	KkConf getConf()

	bool verify(1:string token)
	
	bool sendLog(1:string type, 2:string log)
}
