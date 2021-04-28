use std::io;
fn main() {
		let mut input  = String::new();
		io::stdin()
				.read_line(&mut input)
				.expect("failed to read_line!");
		let num:i32 =  input.trim().parse().expect("please input a number!");

		match num {
			0..=5 => println!(" range is 0-5"),
			6..=9 => println!(" range is 6-9"),
			_ => println!(" range is 5..7"),
		}

}
