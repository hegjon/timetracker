Name: jonny-time
Version: 0.1
Release: 1%{?dist}
Summary: Jonny time tracker

License: ASL 2.0
URL: http://jonnyware.com

Source0: %{name}.tar.gz
BuildArch: noarch

BuildRequires: java-devel >= 1:1.5.0
BuildRequires: maven-local
Requires:      java-headless >= 1:1.5.0

BuildRequires: joda-time
BuildRequires: apache-commons-lang3
BuildRequires: apache-commons-cli
BuildRequires: junit >= 4
BuildRequires: hamcrest
Requires:      apache-commons-codec

BuildRequires: python-docutils

%description
Hmm..

%package javadoc
Summary: Javadoc for %{name}

%description javadoc
%{summary}.

%prep

%setup -c

%build
%mvn_build

pushd src/doc
  rst2man < jtime.rst > ../../target/jtime.1
popd

%install
%mvn_install
install -d %{buildroot}%{_mandir}/man1
install target/jtime.1 %{buildroot}%{_mandir}/man1/jtime.1

%jpackage_script com.jonnyware.timetracker.cli.Main "-Xint" "" jonny-time:joda-time:apache-commons-lang3:apache-commons-cli:apache-commons-codec jtime true

%files -f .mfiles
%{_bindir}/jtime
%doc LICENSE.txt
%doc TODO.txt
%doc %{_mandir}/man1/jtime.1*

%files javadoc -f .mfiles-javadoc
%doc LICENSE.txt

%changelog
* Tue Mar 25 2014 Jonny Heggheim <hegjon@gmail.com> - 1.0-1
- Initial packaging
